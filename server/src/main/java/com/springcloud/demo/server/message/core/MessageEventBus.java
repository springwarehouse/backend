package com.springcloud.demo.server.message.core;

import com.google.common.eventbus.EventBus;

public class MessageEventBus {
    private static final EventBus eventBus = new EventBus("message-event-bus");

    public static void register(Object object) {
        eventBus.register(object);
    }

    public static void unregister(Object object) {
        eventBus.unregister(object);
    }

    public static void post(Object event) {
        eventBus.post(event);
    }
}
