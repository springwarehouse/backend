package com.example.message.data.ws;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class WSMessage {
    /**
     * 消息ID
     */
    private Long id;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 消息路由地址
     */
    private String route;

    /**
     * 消息数据
     */
    private JsonNode data;
}
