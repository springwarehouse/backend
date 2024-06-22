package com.example.demo.server.eventbus.template.event;

import com.example.demo.server.eventbus.common.event.Event;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 结束事件
 */
@Data
@RequiredArgsConstructor
public class TemplateStopEvent implements Event {
    private final String templateId;

    private final Date startTime = new Date();

    @Override
    public String getEventId() {
        return templateId;
    }
}
