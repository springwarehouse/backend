package com.springcloud.demo.server.config;

import com.springcloud.demo.server.config.properties.WebSocketProperty;
import com.springcloud.demo.server.core.handler.WSHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

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
