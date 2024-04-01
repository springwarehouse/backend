package com.springcloud.demo.server.message.core;

import io.netty.util.HashedWheelTimer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageCenter implements InitializingBean, DisposableBean {

    private final HashedWheelTimer timer = new HashedWheelTimer(r -> new Thread(r, "MessageExecutor-timer"));

    private static final int DEFAULT_EXECUTOR_COUNT = 4;

    private List<MessageExecutor> executors;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("MessageCenter init");
        MessageEventBus.register(this);
        timer.start();

        // 创建执行器
        this.executors = new ArrayList<>(DEFAULT_EXECUTOR_COUNT);

        for (int i = 0; i < DEFAULT_EXECUTOR_COUNT; i++) {
            MessageExecutor executor = new MessageExecutor(i);
            executor.startAsync();
            this.executors.add(executor);
        }
    }

    @Override
    public void destroy() throws Exception {
        log.info("MessageCenter destroy");
    }
}