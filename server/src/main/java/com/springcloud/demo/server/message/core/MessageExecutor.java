package com.springcloud.demo.server.message.core;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageExecutor extends AbstractExecutionThreadService {

    private final int id;

    public MessageExecutor(int id) {
        this.id = id;
    }
    
    @Override
    protected String serviceName() {
        return super.serviceName();
    }

    @Override
    protected void run() throws Exception {
        log.info("start executor: {}", serviceName());
    }

    @Override
    protected void triggerShutdown() {
        log.info("stop executor: {}", serviceName());
    }
}
