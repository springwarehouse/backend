package com.example.server.eventbus.common.exception;

import lombok.Getter;

@Getter
public class ArgumentsException extends RuntimeException {

    private final String eventId;

    public ArgumentsException(String eventId, String message) {
        super(String.format("解析参数错误 event(%s), eventType: %s", eventId, message));
        this.eventId = eventId;
    }
}
