package com.springcloud.demo.server.service.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.demo.server.data.ws.WSMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
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

    public void handleMessage(WebSocketSession session, WSMessage message) throws Exception {
        log.info("handleMessage");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("WSService init");
    }
}
