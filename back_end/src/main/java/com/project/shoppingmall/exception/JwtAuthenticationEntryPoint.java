package com.project.shoppingmall.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        if (log.isTraceEnabled()) {
            authException.printStackTrace();
        }
        ErrorCode code = ErrorCode.UNAUTHORIZED;
        Response<Void> errorResponse = Response.error(code.getMessage(), code);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(code.getStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
