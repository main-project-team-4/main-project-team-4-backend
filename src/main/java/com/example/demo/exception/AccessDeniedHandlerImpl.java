package com.example.demo.exception;

import com.example.demo.dto.MessageResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        String message = "인가 오류 발생";
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        response.setStatus(httpStatus.value());

        MessageResponseDto responseDto = new MessageResponseDto(message, httpStatus.value());
        objectMapper.writeValue(response.getWriter(), responseDto);
    }
}
