package com.example.demo.server.eventbus.message;

import com.example.demo.server.eventbus.core.BaseEventAgent;
import com.example.demo.server.eventbus.core.EventCenter;
import com.example.demo.server.eventbus.core.EventFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageAgentFactory implements EventFactory {
    @Override
    public List<BaseEventAgent> getEventAgents(EventCenter eventCenter) {
        return null;
    }
}
