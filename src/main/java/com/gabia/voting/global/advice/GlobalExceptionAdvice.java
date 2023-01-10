package com.gabia.voting.global.advice;

import com.gabia.voting.global.dto.APIResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected APIResponseDTO unknownException(Exception e){
        log.error(e.getMessage(), e);
        return APIResponseDTO.fail(-9999, "알 수 없는 오류가 발생하였습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected APIResponseDTO methodArgumentNotValidException(MethodArgumentNotValidException e){
        return APIResponseDTO.fail(-9000, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

}
