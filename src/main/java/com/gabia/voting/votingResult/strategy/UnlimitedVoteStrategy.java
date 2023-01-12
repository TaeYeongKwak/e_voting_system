package com.gabia.voting.votingResult.strategy;

import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.repository.VotingResultRepository;

public class UnlimitedVoteStrategy extends VoteStrategy{

    public UnlimitedVoteStrategy(VotingResultRepository votingResultRepository) {
        super(votingResultRepository);
    }

    @Override
    public VotingResult vote(VoteRequestDTO voteRequestDTO) {
        VotingResult saveVotingResult = voteRequestDTO.toEntity();
        return votingResultRepository.save(saveVotingResult);
    }
}
