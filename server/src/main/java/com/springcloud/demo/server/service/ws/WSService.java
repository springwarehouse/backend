package com.springcloud.demo.server.service.ws;

import com.springcloud.demo.server.data.ws.WSMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class WSService implements InitializingBean {

    public void addConnection(WebSocketSession session) {
        log.info("addConnection");
    }

    public void removeConnection(WebSocketSession session) {
        log.info("removeConnection");
    }

    public void handleMessage(WebSocketSession session, WSMessage message) throws Exception {
        log.info("handleMessage");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("WSService init");
    }
}
