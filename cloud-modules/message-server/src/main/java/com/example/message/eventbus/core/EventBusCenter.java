package com.example.message.eventbus.core;

import com.google.common.eventbus.EventBus;

/**
 * EventBus中心
 * 被观察者(Observables):维护一个观察者队列,事件产生时,回调所有的观察者
 */
public class EventBusCenter {

    // EventBus的创建
    private static final EventBus eventBus = new EventBus("event-bus");

    // 注册订阅者
    public static void register(Object object) {
        eventBus.register(object);
    }

    // 注销事件
    public static void unregister(Object object) {
        eventBus.unregister(object);
    }

    // 发布事件
    public static void post(Object event) {
        eventBus.post(event);
    }
}