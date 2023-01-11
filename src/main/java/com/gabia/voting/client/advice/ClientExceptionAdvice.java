package com.gabia.voting.client.advice;

import com.gabia.voting.client.exception.*;
import com.gabia.voting.global.dto.APIResponseDTO;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ClientExceptionAdvice {

    @ExceptionHandler(DuplicationClientIdException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected APIResponseDTO duplicationClientIdException(){
        ClientExceptionInfo exceptionInfo = ClientExceptionInfo.DUPLICATION_CLIENT_ID;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected APIResponseDTO clientNotFoundException(){
        ClientExceptionInfo exceptionInfo = ClientExceptionInfo.CLIENT_NOT_FOUND;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

    @ExceptionHandler(PasswordMisMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected APIResponseDTO passwordMisMatchException(){
        ClientExceptionInfo exceptionInfo = ClientExceptionInfo.PASSWORD_MIS_MATCH;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

    @ExceptionHandler(LoginInfoFailDecodingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected APIResponseDTO loginInfoFailDecodingException(){
        ClientExceptionInfo exceptionInfo = ClientExceptionInfo.LOGIN_INFO_FAIL_DECODING;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

    @ExceptionHandler(UnformattedHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected APIResponseDTO unformattedHeaderException(){
        ClientExceptionInfo exceptionInfo = ClientExceptionInfo.UNFORMATTED_HEADER;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

}
