package com.example.message.data.ws;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TemplateRespVO {
    /**
     * 监控的模块名
     */
    private String module;

    /**
     * 监控的ID
     */
    private String templateId;

    /**
     * 监控的时间
     */
    private Date time;

    /**
     * 监控的类型
     */
    private WSEventType type;

    /**
     * 监控数据
     */
    private Object data;
}
