package com.gabia.voting.votingResult.strategy;

import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.repository.VotingResultRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class VoteStrategy {

    protected final VotingResultRepository votingResultRepository;
    public abstract void vote(VoteRequestDTO voteRequestDTO);
}
