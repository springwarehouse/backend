package com.example.message.eventbus.template.event;

import com.example.message.eventbus.common.event.Event;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;


@Data
@RequiredArgsConstructor
public class ChangeEvent implements Event {

    private final String templateId;

    private final Date startTime = new Date();

    private final Double variable;

    @Override
    public String getEventId() {
        return templateId;
    }
}
