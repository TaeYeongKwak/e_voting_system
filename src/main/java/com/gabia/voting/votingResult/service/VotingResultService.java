package com.gabia.voting.votingResult.service;

import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.dto.VoteResultInfoDTO;

public interface VotingResultService {

    public void useVotingRight(Long itemPk, Long clientPk, VoteRequestDTO voteRequestDTO);
    public VoteResultInfoDTO getVoteResult(Long itemPk);

}
