package com.example.demo.server.eventbus.message;

import com.google.common.eventbus.Subscribe;
import com.example.demo.server.eventbus.core.BaseEventAgent;
import com.example.demo.server.eventbus.core.EventCenter;
import com.example.demo.server.eventbus.data.Message;
import lombok.extern.slf4j.Slf4j;

/**
 * 观察者（Observer）：注册到"被观察者"中，接收通知，执行自己的业务
 */
@Slf4j
public class MessageAgent extends BaseEventAgent {

    public MessageAgent(EventCenter eventCenter) {
        super(eventCenter);
    }

    /**
     * 只有通过@Subscribe注解的方法才会被注册进EventBus
     * 而且方法有且只能有1个参数
     *
     * @param message 消息
     */
    @Subscribe
    public void handleEvent(Message message) {
        // Todo
        execute(() -> {
        });
    }
}
