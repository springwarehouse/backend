package com.example.message.eventbus.core;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public abstract class BaseEventAgent {

    protected final EventCenter eventCenter;

    protected final HashedWheelTimer timer;

    protected final ApplicationContext applicationContext;

    public BaseEventAgent(EventCenter eventCenter) {
        this.eventCenter = eventCenter;
        this.applicationContext = eventCenter.getApplicationContext();
        this.timer = eventCenter.getTimer();
    }

    protected void onStart() {
    }

    protected void onStop() {
    }

    public void execute(Runnable runnable) {
        eventCenter.submitTask(this, runnable);
    }

    public void postEvent(Object event) {
        EventBusCenter.post(event);
    }

    public <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public Timeout newTimerTask(Runnable runnable, long delay) {
        return timer.newTimeout(timeout -> {
            execute(runnable);
        }, delay, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
}
