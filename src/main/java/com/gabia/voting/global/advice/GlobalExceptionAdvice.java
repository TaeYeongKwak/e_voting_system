package com.gabia.voting.global.advice;

import com.gabia.voting.global.dto.APIResponseDTO;
import com.gabia.voting.global.exception.GlobalExceptionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected APIResponseDTO unknownException(Exception e){
        log.error(e.getMessage(), e);
        GlobalExceptionInfo exceptionInfo = GlobalExceptionInfo.UNKNOWN;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected APIResponseDTO methodArgumentNotValidException(MethodArgumentNotValidException e){
        return APIResponseDTO.fail(GlobalExceptionInfo.METHOD_ARGUMENT_NOT_VALID.getCode(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected APIResponseDTO constraintViolationException(ConstraintViolationException e){
        String message = e.getMessage().substring(e.getMessage().indexOf(":") + 2);
        return APIResponseDTO.fail(GlobalExceptionInfo.METHOD_ARGUMENT_NOT_VALID.getCode(), message);
    }

}
