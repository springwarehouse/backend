package com.springcloud.demo.server.service.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.demo.server.data.ws.WSMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class WSService implements InitializingBean {

    private final ObjectMapper objectMapper;

    // 新连接 session id -> session
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addConnection(WebSocketSession session) {
        log.info("addConnection");
        sessions.put(session.getId(), session);
    }

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
