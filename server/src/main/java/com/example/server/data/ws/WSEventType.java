package com.example.server.data.ws;

public enum WSEventType {
    TemplateData,  // 监控数据
    StartB, // 开始预警
    StopV,  // 结束预警
    StartC,   // 开始告警
    StopC,    // 结束告警
}
