package com.example.server.eventbus.common.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 接收消息
 */
@Data
@RequiredArgsConstructor
public class ReceiveMessage {

    private final String templateId;
    // 消息
    private final String message;

}
