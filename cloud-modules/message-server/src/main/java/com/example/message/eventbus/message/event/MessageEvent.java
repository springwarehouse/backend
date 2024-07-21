package com.example.message.eventbus.message.event;

import com.example.message.eventbus.common.event.Event;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class MessageEvent implements Event {

    private final String templateId;

    private final Date startTime = new Date();

    private final String message;

    @Override
    public String getEventId() {
        return templateId;
    }
}
