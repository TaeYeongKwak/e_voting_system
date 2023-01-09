package com.gabia.voting.client.advice;

import com.gabia.voting.client.exception.ClientExceptionInfo;
import com.gabia.voting.client.exception.DuplicationClientIdException;
import com.gabia.voting.global.dto.APIResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionAdvice {

    @ExceptionHandler(DuplicationClientIdException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected APIResponseDTO duplicationClientIdException(){
        ClientExceptionInfo exceptionInfo = ClientExceptionInfo.DUPLICATION_CLIENT_ID;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

}
