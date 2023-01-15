package com.gabia.voting.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalExceptionInfo {

    UNKNOWN(-9999, "알 수 없는 오류가 발생하였습니다."),
    METHOD_ARGUMENT_NOT_VALID(-9000, null),
    AUTHENTICATION_ENTRY_POINT(-9001, "해당 기능을 이용하기 위한 권한이 없습니다."),
    ACCESS_DENIED(-9002, "권한이 부족하여 해당 기능을 이용할 수 없습니다.");

    private int code;
    private String message;
}
