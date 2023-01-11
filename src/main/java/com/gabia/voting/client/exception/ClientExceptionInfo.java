package com.gabia.voting.client.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientExceptionInfo {

    DUPLICATION_CLIENT_ID(-1001, "해당 아이디는 이미 사용중입니다."),
    CLIENT_NOT_FOUND(-1002, "해당 사용자가 존재하지 않습니다."),
    PASSWORD_MIS_MATCH(-1003, "비밀번호가 일치하지 않습니다."),
    LOGIN_INFO_FAIL_DECODING(-1004, "로그인 정보를 가져오는데 실패하였습니다."),
    UNFORMATTED_HEADER(-1005, "헤더 형식이 잘못되어 있습니다.");

    private int code;
    private String message;
}
