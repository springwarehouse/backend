package com.example.demo.server.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.server.core.result.CommonResultStatus;
import com.example.demo.server.core.result.Result;
import com.example.demo.server.core.utils.ServletUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理类
 */
@AllArgsConstructor
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        Result<?> result = Result.error(CommonResultStatus.FORBIDDEN);
        String content = objectMapper.writeValueAsString(result);
        ServletUtils.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }


}
