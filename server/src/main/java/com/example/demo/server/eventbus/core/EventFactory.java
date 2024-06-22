package com.example.demo.server.eventbus.core;

import java.util.List;

public interface EventFactory {
    List<BaseEventAgent> getWarningAgents(EventCenter warningCenter);
}
