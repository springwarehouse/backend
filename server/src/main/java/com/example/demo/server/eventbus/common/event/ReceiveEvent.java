package com.example.demo.server.eventbus.common.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 接受事件
 */
@Data
@RequiredArgsConstructor
public class ReceiveEvent {
    private final String templateId;
    // 发送时间
    private final Date time;
    // 数据值
    private final Double variable;
}
