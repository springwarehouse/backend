package com.example.server.eventbus.message;

import com.example.server.eventbus.core.BaseEventAgent;
import com.example.server.eventbus.core.EventCenter;
import com.example.server.eventbus.core.EventFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageAgentFactory implements EventFactory {
    @Override
    public List<BaseEventAgent> getEventAgents(EventCenter eventCenter) {
        List<BaseEventAgent> agents = new ArrayList<>();
        agents.add(new MessageAgent(eventCenter));
        return agents;
    }
}
