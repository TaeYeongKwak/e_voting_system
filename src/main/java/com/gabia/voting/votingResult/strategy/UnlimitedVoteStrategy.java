package com.gabia.voting.votingResult.strategy;

import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.repository.VotingResultRepository;

public class UnlimitedVoteStrategy implements VoteStrategy{

    @Override
    public VotingResult vote(VoteRequestDTO voteRequestDTO, VotingResultRepository votingResultRepository) {
        VotingResult saveVotingResult = voteRequestDTO.toEntity();
        return votingResultRepository.save(saveVotingResult);
    }
}
