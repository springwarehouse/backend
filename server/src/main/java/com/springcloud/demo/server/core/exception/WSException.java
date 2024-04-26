package com.springcloud.demo.server.core.exception;

/**
 * WebSocket异常
 */
public class WSException extends Exception {

    /**
     * 构造函数
     *
     * @param message 异常信息
     */
    public WSException(String message) {
        super(message);
    }
}
