package com.gabia.voting.item.advice;

import com.gabia.voting.global.dto.APIResponseDTO;
import com.gabia.voting.item.exception.ItemExceptionInfo;
import com.gabia.voting.item.exception.ItemNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ItemExceptionAdvice {

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponseDTO itemNotFoundException(){
        ItemExceptionInfo exceptionInfo = ItemExceptionInfo.ITEM_NOT_FOUND;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

}
