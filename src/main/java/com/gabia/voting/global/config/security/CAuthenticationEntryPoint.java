package com.gabia.voting.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.voting.global.dto.APIResponseDTO;
import com.gabia.voting.global.exception.GlobalExceptionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
public class CAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        GlobalExceptionInfo exceptionInfo = GlobalExceptionInfo.AUTHENTICATION_ENTRY_POINT;
        String json = objectMapper.writeValueAsString(APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage()));
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}
