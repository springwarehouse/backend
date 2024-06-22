package com.example.demo.server.eventbus.core;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.ServiceManager;
import io.netty.util.HashedWheelTimer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 事件中心
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EventCenter implements InitializingBean, DisposableBean {

    private final HashedWheelTimer timer = new HashedWheelTimer(r -> new Thread(r, "EventExecutor-timer"));

    private static final int DEFAULT_EXECUTOR_COUNT = 4;

    private final ApplicationContext applicationContext;

    private ServiceManager executorManager;

    private List<EventExecutor> executors;

    private final List<EventFactory> eventFactories;

    private List<BaseEventAgent> allAgents;

    /**
     * 保证同一个 agent 的任务必定在同一个线程中执行
     */
    public void submitTask(BaseEventAgent agent, Runnable runnable) {
        int index = agent.hashCode() % DEFAULT_EXECUTOR_COUNT;
        if (index < 0) {
            index += DEFAULT_EXECUTOR_COUNT;
        }
        executors.get(index).submitTask(runnable);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public HashedWheelTimer getTimer() {
        return timer;
    }

    @Subscribe
    private void handleDeadEvent(DeadEvent event) {
        log.warn("dead event: {}", event);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // handleDeadEvent
        log.info("MessageCenter init");
        EventBusCenter.register(this);
        timer.start();

        // 创建所有执行器
        this.executors = new ArrayList<>(DEFAULT_EXECUTOR_COUNT);

        for (int i = 0; i < DEFAULT_EXECUTOR_COUNT; i++) {
            this.executors.add(new EventExecutor(i));
        }
        this.executorManager = new ServiceManager(executors);
        this.executorManager.startAsync();
        // 等待所有 executor 启动完成
        this.executorManager.awaitHealthy();

        // 加载所有 agent
        loadAllAgents();
    }

    @Override
    public void destroy() throws Exception {
        log.info("stop all executors");
        this.executorManager.stopAsync();
        try {
            this.executorManager.awaitStopped(Duration.ofSeconds(5));
        } catch (TimeoutException e) {
            // ignore
        }
        timer.stop();
        // 停止所有 agent
        log.info("stop all agents");
        allAgents.forEach(agent -> {
            // 注销事件总线
            EventBusCenter.unregister(agent);
            // 调用生命周期 onStop
            // 线程池已经停止, 所以直接调用
            try {
                agent.onStop();
            } catch (Exception e) {
                log.error("agent onStop exception", e);
            }
        });
    }

    private void loadAllAgents() {
        List<BaseEventAgent> allAgents = new ArrayList<>();
        eventFactories.forEach(factory -> {
            List<BaseEventAgent> agents = factory.getWarningAgents(this);
            if (CollectionUtils.isEmpty(agents)) {
                return;
            }
            agents.forEach(agent -> {
                // 调用生命周期 onStart
                submitTask(agent, agent::onStart);
            });
            allAgents.addAll(agents);
        });
        this.allAgents = allAgents;

        // 启动之后再注册到事件总线
        this.allAgents.forEach(EventBusCenter::register);
    }
}