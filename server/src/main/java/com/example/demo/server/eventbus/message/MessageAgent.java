package com.example.demo.server.eventbus.message;

import com.example.demo.server.eventbus.common.event.ReceiveMessage;
import com.example.demo.server.eventbus.message.event.MessageEvent;
import com.google.common.eventbus.Subscribe;
import com.example.demo.server.eventbus.core.BaseEventAgent;
import com.example.demo.server.eventbus.core.EventCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 观察者（Observer）：注册到"被观察者"中，接收通知，执行自己的业务
 */
@Slf4j
@Component
public class MessageAgent extends BaseEventAgent {

    public MessageAgent(EventCenter eventCenter) {
        super(eventCenter);
    }

    /**
     * 只有通过@Subscribe注解的方法才会被注册进EventBus
     * 而且方法有且只能有1个参数
     *
     * @param event 消息
     */
    @Subscribe
    public void handleEvent(ReceiveMessage event) {
        // Todo
        // 发布事件
        postEvent(new MessageEvent(event.getTemplateId(), event.getMessage()));
    }
}
