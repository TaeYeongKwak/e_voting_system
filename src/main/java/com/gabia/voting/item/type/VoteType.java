package com.gabia.voting.item.type;

import com.gabia.voting.votingResult.repository.VotingResultRepository;
import com.gabia.voting.votingResult.strategy.FirstServedLimitedVoteStrategy;
import com.gabia.voting.votingResult.strategy.UnlimitedVoteStrategy;
import com.gabia.voting.votingResult.strategy.VoteStrategy;

public enum VoteType {

    UNLIMITED{
        @Override
        public VoteStrategy createStrategy(VotingResultRepository votingResultRepository) {
            return new UnlimitedVoteStrategy(votingResultRepository);
        }
    },
    FIRST_SERVED_LIMITED {
        @Override
        public VoteStrategy createStrategy(VotingResultRepository votingResultRepository) {
            return new FirstServedLimitedVoteStrategy(votingResultRepository);
        }
    };

    abstract public VoteStrategy createStrategy(VotingResultRepository votingResultRepository);

}
