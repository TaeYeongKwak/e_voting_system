package com.gabia.voting.votingResult.strategy;

import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.repository.VotingResultRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class VoteStrategy {

    protected final VotingResultRepository votingResultRepository;
    public abstract VotingResult vote(VoteRequestDTO voteRequestDTO);

    public VotingResult saveVotingResult(Vote vote, VoteRequestDTO voteRequestDTO){
        VotingResult saveVotingResult = voteRequestDTO.toEntity();
        saveVotingResult = votingResultRepository.save(saveVotingResult);
        vote.updateVotingCount(saveVotingResult.getOpinion(), saveVotingResult.getCount());
        return saveVotingResult;
    }
}
