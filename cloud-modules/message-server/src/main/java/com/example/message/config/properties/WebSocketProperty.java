package com.example.message.config.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * WebSocket属性
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "ws")
public class WebSocketProperty {
    /**
     * WebSocket路径
     */
    private String path;
}
