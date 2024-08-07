package com.example.message.eventbus.core;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 事件执行器
 */
@Slf4j
public class EventExecutor extends AbstractExecutionThreadService {

    private final Runnable STOP = () -> {
    };
    private final BlockingQueue<Runnable> actionQueue = new LinkedBlockingQueue<>();

    private final int id;

    private final AtomicLong counter = new AtomicLong(0);

    public EventExecutor(int id) {
        this.id = id;
    }

    @Nonnull
    @Override
    protected String serviceName() {
        return "EventExecutor-" + this.id;
    }

    @Override
    protected void run() throws Exception {
        log.info("start executor: {}", serviceName());
        while (isRunning()) {
            try {
                Runnable action = actionQueue.take();
                if (action == STOP) {
                    break;
                }
                counter.incrementAndGet();
                action.run();
            } catch (Exception e) {
                log.error("执行 EventExecutor 时发生异常", e);
            }
        }
        log.info("stop executor: {}", serviceName());
    }

    @Override
    protected void triggerShutdown() {
        actionQueue.add(STOP);
    }

    public void submitTask(Runnable action) {
        actionQueue.add(action);
    }

    public void printState() {
        log.info("{}: done tasks: {}, queue tasks: {}", serviceName(), counter.get(), actionQueue.size());
    }
}