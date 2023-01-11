package com.gabia.voting.votingResult.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VotingResultExceptionInfo {

    EXCEED_LIMITED_VOTING_RIGHT_COUNT(-3001, "제한된 투표 수를 넘었습니다.");

    private int code;
    private String message;

}
