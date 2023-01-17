package com.gabia.voting.votingResult.strategy;

import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.exception.ExceedLimitedVotingRightCountException;
import com.gabia.voting.votingResult.repository.VotingResultRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirstServedLimitedVoteStrategy extends VoteStrategy{

    public static final int LIMITED_COUNT = 10;

    public FirstServedLimitedVoteStrategy(VotingResultRepository votingResultRepository) {
        super(votingResultRepository);
    }

    @Override
    public VotingResult vote(VoteRequestDTO voteRequestDTO) {
        Vote vote = votingResultRepository.findByVotePkForUpdate(voteRequestDTO.getVote().getVotePk());
        int sumCount = vote.getAgreementCount() + vote.getOppositionCount() + vote.getGiveUpCount();
        if (sumCount >= LIMITED_COUNT)
            throw new ExceedLimitedVotingRightCountException();

        int possibleCount = LIMITED_COUNT - sumCount;
        voteRequestDTO.setCount(voteRequestDTO.getCount() > possibleCount? possibleCount : voteRequestDTO.getCount());
        return saveVotingResult(vote, voteRequestDTO);
    }
}
