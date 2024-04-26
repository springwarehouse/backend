package com.springcloud.demo.server.service.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.demo.server.core.exception.WSException;
import com.springcloud.demo.server.data.constant.WSRequestType;
import com.springcloud.demo.server.data.ws.WSMessage;
import com.springcloud.demo.server.data.ws.WSResponse;
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

    private final ObjectMapper objectMapper;

    // 使用线程安全的Map储存会话
    // 新连接 session id -> session
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    /**
     * 记录用户的连接标识，便于后面发信息，这里我是将id记录在Map集合中
     *
     * @param session
     */
    public void addConnection(WebSocketSession session) {
        log.info("addConnection");
        sessions.put(session.getId(), session);
    }

    /**
     * 连接已关闭，根据id移除在Map集合中的记录
     *
     * @param session
     */
    public void removeConnection(WebSocketSession session) {
        log.info("removeConnection");
        sessions.remove(session.getId());
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
    }
}
