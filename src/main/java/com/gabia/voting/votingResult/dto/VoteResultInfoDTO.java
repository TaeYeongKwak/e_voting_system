package com.gabia.voting.votingResult.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoteResultInfoDTO {

    private List<OpinionCountDTO> shareholderResult;
    private List<SimpleVotingResultDTO> managerResult;


}

