package com.example.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class TestController {
    /**
     * 测试getway
     * @return
     */
    @PostMapping("/test")
    public String info() {
        return "test...";
    }
}
