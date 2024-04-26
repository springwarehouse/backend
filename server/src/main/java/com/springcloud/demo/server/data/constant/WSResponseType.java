package com.springcloud.demo.server.data.constant;

/**
 * WebSocket响应类型
 */
public enum WSResponseType {

    /**
     * 响应
     */
    response,

    /**
     * 推送
     */
    push,

    /**
     * 心跳
     */
    heartbeat,
}
