package com.example.demo.server.eventbus.template;

import com.example.demo.server.eventbus.core.BaseEventAgent;
import com.example.demo.server.eventbus.core.EventCenter;
import com.example.demo.server.eventbus.core.EventFactory;
import com.example.demo.server.eventbus.data.TemplateInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class TemplateAgentFactory implements EventFactory {
    @Override
    public List<BaseEventAgent> getEventAgents(EventCenter eventCenter) {
        List<String> list = new ArrayList();
        list.add("template1");
        return list.stream().map((item) -> {
            TemplateInfo templateInfo = new TemplateInfo();
            templateInfo.setTemplateId(item);
            return  new TemplateAgent(eventCenter, templateInfo);
        }).collect(Collectors.toList());
    }
}
