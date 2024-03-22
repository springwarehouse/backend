package com.springcloud.demo.server.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.demo.server.core.result.CommonResultStatus;
import com.springcloud.demo.server.core.result.Result;
import com.springcloud.demo.server.core.utils.ServletUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        Result<?> result = Result.error(CommonResultStatus.NOT_LOGIN);
        String content = objectMapper.writeValueAsString(result);
        ServletUtils.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
}
