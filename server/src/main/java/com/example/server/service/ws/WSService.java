package com.example.server.service.ws;

import com.example.server.eventbus.core.EventBusCenter;
import com.example.server.core.exception.WSException;
import com.example.server.data.constant.WSRequestType;
import com.example.server.data.ws.WSMessage;
import com.example.server.data.ws.WSResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WSService implements InitializingBean {

    // 用于序列化和反序列化
    private final ObjectMapper objectMapper;

    // 使用线程安全的Map储存会话
    // 新连接 session id -> session
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private final TemplateHandler templateHandler;

    private final MessageHandler messageHandler;

    /**
     * 记录用户的连接标识，便于后面发信息，这里我是将id记录在Map集合中
     *
     * @param session
     */
    public void addConnection(WebSocketSession session) {
        log.info("addConnection");
        // 添加会话
        sessions.put(session.getId(), session);
        messageHandler.handle(this, session);
    }

    /**
     * 连接已关闭，根据id移除在Map集合中的记录
     *
     * @param session
     */
    public void removeConnection(WebSocketSession session) {
        log.info("removeConnection");
        // 移除会话
        // TODO: 分开后可以移除
        sessions.remove(session.getId());
        templateHandler.removeSession(session);
        messageHandler.removeSession(session);
    }

    /**
     * 处理消息
     *
     * @param session
     * @param message
     * @throws Exception
     */
    public void handleMessage(WebSocketSession session, WSMessage message) throws Exception {
        log.info("handleMessage");
        // 校验消息类型
        if ((message.getType()).equals(WSRequestType.HEARTBEAT)) {
            // 心跳消息
            log.info("heartbeat message: {}", message);
            // 处理心跳
            handleHeartBeat(session, message);
        } else if ((message.getType()).equals(WSRequestType.REQUEST)) {
            // 请求消息
            log.info("request message: {}", message);
            // 获取请求路由
            String route = message.getRoute();
            // 校验请求路由
            if (!StringUtils.hasText(route)) {
                throw new WSException("unknown request route: " + route);
            }
            if (route.startsWith(TemplateHandler.Prefix)) {
                templateHandler.handle(this, session, message);
            } else {
                throw new WSException("unknown request route: " + route);
            }
        } else {
            throw new WSException("unknown type: " + message.getType());

        }
    }

    /**
     * 处理心跳
     *
     * @param session
     * @param message
     */
    private void handleHeartBeat(WebSocketSession session, WSMessage message) throws Exception {
        log.info("handleHeartbeat");
        // 返回心跳
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(WSResponse.heartbeat())));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("WSService init");
        EventBusCenter.register(templateHandler);
        EventBusCenter.register(messageHandler);
    }

    public <T> void responseData(WebSocketSession session, Long id, String route, T data) throws Exception {
        WSResponse<T> response = WSResponse.response(id, route, data);
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
    }
}
