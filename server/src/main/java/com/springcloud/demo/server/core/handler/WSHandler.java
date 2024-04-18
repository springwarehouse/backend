package com.springcloud.demo.server.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.demo.server.core.exception.WSException;
import com.springcloud.demo.server.data.ws.WSMessage;
import com.springcloud.demo.server.service.ws.WSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WSHandler extends TextWebSocketHandler {

    private final WSService wsService;

    private final ObjectMapper objectMapper;

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("afterConnectionEstablished: id={}, uri={}", session.getId(), session.getUri());
        }
        session.setTextMessageSizeLimit(1024 * 1024);
        session.setBinaryMessageSizeLimit(1024 * 1024);
        wsService.addConnection(session);
    }

    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("handleTextMessage: id={}, payload={}", session.getId(), message.getPayload());
        }
        String payload = message.getPayload();
        try {
            WSMessage wsMessage = objectMapper.readValue(payload, WSMessage.class);
            if (wsMessage.getType() == null) {
                throw new RuntimeException("message type cannot be null");
            }
            wsService.handleMessage(session, wsMessage);
        } catch (Exception e) {
            log.error("handleMessage exception", e);
            CloseStatus closeStatus;
            if (e instanceof WSException) {
                closeStatus = CloseStatus.POLICY_VIOLATION.withReason(e.getMessage());
            } else {
                closeStatus = CloseStatus.SERVER_ERROR.withReason("server error");
            }
            try {
                session.close(closeStatus);
            } catch (Exception e1) {
                // ignore
            }
        }
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("handleTransportError: {}", session, exception);
        try {
            session.close();
        } catch (Exception e1) {
            // ignore
        }
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("afterConnectionClosed: id={}, status={}", session.getId(), status);
        }
        wsService.removeConnection(session);
    }
}
