package com.gabia.voting.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalExceptionInfo {

    UNKNOWN(-9999, "알 수 없는 오류가 발생하였습니다."),
    METHOD_ARGUMENT_NOT_VALID(-9000, null);

    private int code;
    private String message;
}


