package com.example.server.data.ws;

import lombok.Data;

@Data
public class TemplateReqVO {
    /**
     * 监控的模块名
     */
    private String module;

    /**
     * 监控的ID
     */
    private String templateId;
}
