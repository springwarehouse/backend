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

/**
 * WebSocket处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WSHandler extends TextWebSocketHandler {

    private final WSService wsService;

    private final ObjectMapper objectMapper;

    /**
     * 当客户端建立连接时调用，用于执行连接建立后的操作
     *
     * @param session
     * @throws Exception
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // 用于判断某个级别的日志是否被允许输出，多用于性能优化
        // (附：日志输出级别包括：All、TRACE、DEBUG、INFO、WARN、ERROR、FATAL、OFF)
        if (log.isTraceEnabled()) {
            log.trace("afterConnectionEstablished: id={}, uri={}", session.getId(), session.getUri());
        }

        // 设置最大报文大小，如果报文过大则会报错，避免由于过大的消息导致网络问题或应用程序崩溃
        // 会覆盖config中的配置
        session.setTextMessageSizeLimit(1024 * 1024);
        session.setBinaryMessageSizeLimit(1024 * 1024);
        wsService.addConnection(session);
    }

    /**
     * 当接收到消息时调用，用于处理客户端发送的消息。
     *
     * @param session
     * @param message 通过调用getPayload() 接受前台消息
     * @throws Exception
     */
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("handleTextMessage: id={}, payload={}", session.getId(), message.getPayload());
        }
        String payload = message.getPayload();
        try {
            // 将json字符串转化为对象
            WSMessage wsMessage = objectMapper.readValue(payload, WSMessage.class);
            if (wsMessage.getType() == null) {
                throw new RuntimeException("message type cannot be null");
            }
            // 处理消息
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
     * 当连接发生错误时调用，用于处理连接错误。
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("handleTransportError: {}", session, exception);
        try {
            // 如果会话是打开的则关闭会话
            if (session.isOpen()) {
                // 关闭会话
                session.close(CloseStatus.SERVER_ERROR);
            }
        } catch (Exception e1) {
            // ignore
        }
    }

    /**
     * 当连接关闭时调用，用于执行连接关闭后的操作。
     *
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
