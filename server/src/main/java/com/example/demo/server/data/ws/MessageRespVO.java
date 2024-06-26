package com.example.demo.server.data.ws;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MessageRespVO {
    private String templateId;

    private String message;

    private Date time;
}
