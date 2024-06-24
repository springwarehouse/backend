package com.example.demo.server.service.ws;

import com.example.demo.server.core.exception.WSException;
import com.example.demo.server.data.ws.WSMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {
    public static final String Prefix = "message.";

    private final ObjectMapper objectMapper;

    public void handle(WSService wsService, WebSocketSession session, WSMessage wsMessage) throws Exception {
        String route = wsMessage.getRoute().substring(Prefix.length());
        throw new WSException("unknown route: " + wsMessage.getRoute());
    }
}
