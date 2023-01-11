package com.gabia.voting.item.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemExceptionInfo {

    ITEM_NOT_FOUND(-2001, "해당 안건이 존재하지 않습니다."),
    VOTE_NOT_FOUND(-2002, "해당 안건에 대한 투표가 존재하지 않습니다.");

    private int code;
    private String message;

}
