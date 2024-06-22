package com.example.demo.server.eventbus.common.event;

import java.util.Date;

/**
 * 事件. 是系统向外部发送的事件
 */
public interface Event {
    /**
     * 获取事件ID
     */
    String getEventId();

    /**
     * 获取事件发布时间
     */
    Date getStartTime();
}
