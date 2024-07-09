package com.example.server.service.ws;

import com.example.server.data.ws.MessageRespVO;
import com.example.server.data.ws.WSResponse;
import com.example.server.eventbus.message.event.MessageEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();;

    private final ObjectMapper objectMapper;

    public void handle(WSService wsService, WebSocketSession session) {
        // 添加会话
        sessions.put(session.getId(), session);
    }

    @Subscribe
    public void onEvent(MessageEvent e) {
        MessageRespVO respVO = MessageRespVO.builder()
                .templateId(e.getEventId())
                .time(e.getStartTime())
                .message(e.getMessage())
                .build();
        broadcast(respVO);
    }

    private void broadcast(MessageRespVO resp) {
        try {
            String response = objectMapper.writeValueAsString(WSResponse.push("message.data", resp));
            sessions.values().forEach((session) -> {
                try {
                    session.sendMessage(new TextMessage(response));
                } catch (Exception ex) {
                    log.error("broadcast message to session error", ex);
                }
            });
        } catch (Exception e1) {
            log.error("broadcast message to session error", e1);
        }
    }

    // 服务端主动关闭 session 时, 需要将 session 从列表中移除
    public void removeSession(WebSocketSession session) {
        if (log.isDebugEnabled()) {
            log.debug("message remove sessioin: {}", session.getId());
        }
        sessions.remove(session.getId());
        if (log.isDebugEnabled()) {
            log.debug("message remove success: {}", session.getId());
        }
    }
}
