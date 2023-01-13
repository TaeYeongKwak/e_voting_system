package com.gabia.voting.votingResult.service;

import com.gabia.voting.client.type.ClientType;
import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.dto.VoteResultInfoDTO;
import com.gabia.voting.votingResult.entity.VotingResult;

public interface VotingResultService {

    public VotingResult useVotingRight(Long itemPk, Long clientPk, VoteRequestDTO voteRequestDTO);
    public VoteResultInfoDTO getVoteResult(Long itemPk, Long clientPk);

}
