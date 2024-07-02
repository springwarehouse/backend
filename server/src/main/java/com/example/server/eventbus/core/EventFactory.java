package com.example.server.eventbus.core;

import java.util.List;

public interface EventFactory {
    List<BaseEventAgent> getEventAgents(EventCenter eventCenter);
}
