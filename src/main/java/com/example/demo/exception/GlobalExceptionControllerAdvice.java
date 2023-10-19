package com.example.demo.exception;

import com.example.demo.dto.MessageResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j(topic = "전역적 에러 처리")
@Component
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public MessageResponseDto handleIllegalArgumentException(HttpServletResponse response, IllegalArgumentException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        response.setStatus(httpStatus.value());

        log.warn(ex.getMessage());
        return new MessageResponseDto(ex.getMessage(), httpStatus.value());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public MessageResponseDto handleIllegalArgumentException(HttpServletResponse response, AccessDeniedException ex) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        response.setStatus(httpStatus.value());

        log.warn(ex.getMessage());
        return new MessageResponseDto(ex.getMessage(), httpStatus.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MessageResponseDto handleIllegalArgumentException(HttpServletResponse response, MethodArgumentNotValidException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        response.setStatus(httpStatus.value());

        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        log.warn(fieldError.getDefaultMessage());
        return new MessageResponseDto(fieldError.getDefaultMessage(), httpStatus.value());
    }
}
