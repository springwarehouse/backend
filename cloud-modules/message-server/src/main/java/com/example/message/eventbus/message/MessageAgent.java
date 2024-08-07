package com.example.message.eventbus.message;

import com.example.message.eventbus.common.event.ReceiveMessage;
import com.example.message.eventbus.message.event.MessageEvent;
import com.example.message.eventbus.core.BaseEventAgent;
import com.example.message.eventbus.core.EventCenter;
import com.google.common.eventbus.Subscribe;
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
