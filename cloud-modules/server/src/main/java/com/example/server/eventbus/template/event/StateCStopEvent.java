package com.example.server.eventbus.template.event;

import com.example.server.eventbus.common.event.Event;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 结束事件C
 */
@Data
@RequiredArgsConstructor
public class StateCStopEvent implements Event {
    private final String templateId;

    private final Date startTime = new Date();

    @Override
    public String getEventId() {
        return templateId;
    }
}
