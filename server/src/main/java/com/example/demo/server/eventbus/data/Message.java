package com.example.demo.server.eventbus.data;

import lombok.Data;

@Data
public class Message {
    // id
    private String id;
    // 内容
    private String info;
    // 时间
    private Data time;
}
