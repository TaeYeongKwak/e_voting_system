package com.gabia.voting.votingResult.advice;

import com.gabia.voting.global.dto.APIResponseDTO;
import com.gabia.voting.votingResult.exception.DuplicateVoteResultException;
import com.gabia.voting.votingResult.exception.ExceedLimitedVotingRightCountException;
import com.gabia.voting.votingResult.exception.VotingResultExceptionInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VotingResultExceptionAdvice {

    @ExceptionHandler(ExceedLimitedVotingRightCountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected APIResponseDTO exceedLimitedVotingRightCountException(){
        VotingResultExceptionInfo exceptionInfo = VotingResultExceptionInfo.EXCEED_LIMITED_VOTING_RIGHT_COUNT;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

    @ExceptionHandler(DuplicateVoteResultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected APIResponseDTO duplicateVoteResultException(){
        VotingResultExceptionInfo exceptionInfo = VotingResultExceptionInfo.DUPLICATE_VOTE_RESULT;
        return APIResponseDTO.fail(exceptionInfo.getCode(), exceptionInfo.getMessage());
    }

}
