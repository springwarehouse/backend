package com.example.message.eventbus.core;

import java.util.List;

public interface EventFactory {
    List<BaseEventAgent> getEventAgents(EventCenter eventCenter);
}
