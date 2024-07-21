package com.example.message.eventbus.message;

import com.example.message.eventbus.core.BaseEventAgent;
import com.example.message.eventbus.core.EventCenter;
import com.example.message.eventbus.core.EventFactory;
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
