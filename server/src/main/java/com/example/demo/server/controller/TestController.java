package com.example.demo.server.controller;

import com.example.demo.server.core.result.Result;
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
@RequestMapping("/test")
public class TestController {
    @Data
    public static class ReqVO {}

    @PostMapping("/sendData")
    public Result<String> onSendData(@RequestBody ReqVO req) {
        return Result.success("ok");
    }
}
