package com.example.demo.server.eventbus.template;

import com.example.demo.server.eventbus.core.BaseEventAgent;
import com.example.demo.server.eventbus.core.EventCenter;
import com.example.demo.server.eventbus.core.EventFactory;

import java.util.List;

public class TemplateAgentFactory implements EventFactory {
    @Override
    public List<BaseEventAgent> getWarningAgents(EventCenter warningCenter) {
        return null;
    }
}
