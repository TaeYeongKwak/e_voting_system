package com.gabia.voting.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.voting.global.dto.APIResponseDTO;
import com.gabia.voting.global.exception.GlobalExceptionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
public class CAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        GlobalExceptionInfo exceptionInfo = GlobalExceptionInfo.ACCESS_DENIED;
        String json = objectMapper.writeValueAsString(APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage()));
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}
