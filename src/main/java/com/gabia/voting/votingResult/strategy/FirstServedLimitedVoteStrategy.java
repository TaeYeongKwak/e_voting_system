package com.gabia.voting.votingResult.strategy;

import com.gabia.voting.votingResult.dto.VoteRequestDTO;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.exception.ExceedLimitedVotingRightCountException;
import com.gabia.voting.votingResult.repository.VotingResultRepository;

import javax.transaction.Transactional;

public class FirstServedLimitedVoteStrategy extends VoteStrategy{

    public static final int LIMITED_COUNT = 10;

    public FirstServedLimitedVoteStrategy(VotingResultRepository votingResultRepository) {
        super(votingResultRepository);
    }

    @Transactional
    @Override
    public VotingResult vote(VoteRequestDTO voteRequestDTO) {
        int sumCount = votingResultRepository.findAllByVote(voteRequestDTO.getVote()).stream().mapToInt(vr -> vr.getCount()).sum();
        if (sumCount >= LIMITED_COUNT)
            throw new ExceedLimitedVotingRightCountException();

        int possibleCount = LIMITED_COUNT - sumCount;
        voteRequestDTO.setCount(voteRequestDTO.getCount() > possibleCount? possibleCount : voteRequestDTO.getCount());
        VotingResult saveVotingResult = voteRequestDTO.toEntity();
        return votingResultRepository.save(saveVotingResult);
    }
}
