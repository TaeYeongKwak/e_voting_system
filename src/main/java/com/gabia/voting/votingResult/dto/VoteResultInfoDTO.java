package com.gabia.voting.votingResult.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteResultInfoDTO {

    private OpinionCountDTO shareholderResult;
    private List<SimpleVotingResultDTO> managerResult;

    public VoteResultInfoDTO(OpinionCountDTO shareholderResult) {
        this.shareholderResult = shareholderResult;
    }

    public void setManagerResult(List<SimpleVotingResultDTO> managerResult){
        this.managerResult = managerResult;
    }
}

