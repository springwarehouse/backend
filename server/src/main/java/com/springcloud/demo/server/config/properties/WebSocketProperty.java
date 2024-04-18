package com.springcloud.demo.server.config.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@Data
@ConfigurationProperties(prefix = "ws")
public class WebSocketProperty {
    /**
     * WebSocket路径
     */
    private String path;
}
