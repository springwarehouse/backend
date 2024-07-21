package com.example.message.eventbus.template.event;

import com.example.message.eventbus.common.event.Event;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 开始事件C
 */
@Data
@RequiredArgsConstructor
public class StateCStartEvent implements Event {

    private final String templateId;

    private final Date startTime = new Date();

    @Override
    public String getEventId() {
        return templateId;
    }
}
