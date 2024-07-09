package com.example.server.controller;

import com.example.server.core.result.Result;
import com.example.server.eventbus.common.event.ReceiveMessage;
import com.example.server.eventbus.core.EventBusCenter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/provider")
public class TestController {
    @Data
    public static class ReqVO {
        private String id;
        private String message;
    }

    @PostMapping("/sendData")
    public Result<String> onSendData(@RequestBody ReqVO req) {
        EventBusCenter.post(new ReceiveMessage(req.getId(),req.getMessage()));
        return Result.success("ok");
    }

    @PostMapping("/test1")
    public Result<String> test1() {
        return Result.success("test1");
    }

    @PostMapping("/test2")
    public Result<String> test2() {
        return Result.success("test2");
    }
}
