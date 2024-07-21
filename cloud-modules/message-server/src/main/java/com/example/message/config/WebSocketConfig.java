package com.example.message.config;

import com.example.message.config.properties.WebSocketProperty;
import com.example.message.controller.WSHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket配置类
 */
@AllArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    /**
     * WebSocket属性文件
     */
    private final WebSocketProperty webSocketProperty;

    /**
     * WebSocket处理器
     */
    private final WSHandler wsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler, webSocketProperty.getPath()).setAllowedOrigins("*");
    }
}
