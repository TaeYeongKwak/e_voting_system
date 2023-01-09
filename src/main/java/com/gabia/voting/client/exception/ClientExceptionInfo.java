package com.gabia.voting.client.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientExceptionInfo {

    DUPLICATION_CLIENT_ID(-1001, "해당 아이디는 이미 사용중입니다.");

    private int code;
    private String message;
}
