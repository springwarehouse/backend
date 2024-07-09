package com.example.server.controller;

import com.example.server.core.exception.WSException;
import com.example.server.data.ws.WSMessage;
import com.example.server.service.ws.WSService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 要使用WebSocket的相关功能,需要继承TextWebSocketHandler类来实现我们自己的WSHandler类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WSHandler extends TextWebSocketHandler {

    private final WSService wsService;

    private final ObjectMapper objectMapper;

    /**
     * 在WebSocket连接成功建立后被自动调用
     * @param session
     * @throws Exception
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("afterConnectionEstablished: id={}, uri={}", session.getId(), session.getUri());
        }
        session.setTextMessageSizeLimit(1024 * 1024);
        session.setBinaryMessageSizeLimit(1024 * 1024);
        wsService.addConnection(session);
    }

    /**
     * 在WebSocket接收到文本消息时被自动调用。常常用于收到的消息进行转发
     * @param session
     * @param message
     * @throws Exception
     */
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("handleTextMessage: id={}, payload={}", session.getId(), message.getPayload());
        }
        // 接收客户端的消息 message.getPayload()消息体
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

    /**
     * 在连接出现异常时被自动调用
     * @param session
     * @param exception
     * @throws Exception
     */
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("handleTransportError: {}", session, exception);
        try {
            session.close();
        } catch (Exception e1) {
            // ignore
        }
    }

    /**
     * 在连接正常关闭后被自动调用
     * @param session
     * @param status
     * @throws Exception
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("afterConnectionClosed: id={}, status={}", session.getId(), status);
        }
        wsService.removeConnection(session);
    }
}
