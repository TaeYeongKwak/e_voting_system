package com.gabia.voting.item.advice;

import com.gabia.voting.global.dto.APIResponseDTO;
import com.gabia.voting.item.exception.*;
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponseDTO itemNotFoundException(){
        ItemExceptionInfo exceptionInfo = ItemExceptionInfo.ITEM_NOT_FOUND;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

    @ExceptionHandler(VoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponseDTO voteNotFoundException(){
        ItemExceptionInfo exceptionInfo = ItemExceptionInfo.VOTE_NOT_FOUND;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

    @ExceptionHandler(InputErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponseDTO inputErrorException(){
        ItemExceptionInfo exceptionInfo = ItemExceptionInfo.INPUT_ERROR;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

    @ExceptionHandler(NotActiveVoteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected APIResponseDTO notActiveVoteException(){
        ItemExceptionInfo exceptionInfo = ItemExceptionInfo.NOT_ACTIVE_VOTE;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

}
