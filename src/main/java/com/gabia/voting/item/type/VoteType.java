package com.gabia.voting.item.type;

import com.gabia.voting.votingResult.repository.VotingResultRepository;
import com.gabia.voting.votingResult.strategy.FirstServedLimitedVoteStrategy;
import com.gabia.voting.votingResult.strategy.UnlimitedVoteStrategy;
import com.gabia.voting.votingResult.strategy.VoteStrategy;

public enum VoteType {

    UNLIMITED{
        @Override
        protected VoteStrategy createStrategy(VotingResultRepository votingResultRepository) {
            return new UnlimitedVoteStrategy(votingResultRepository);
        }
    },
    FIRST_SERVED_LIMITED {
        @Override
        protected VoteStrategy createStrategy(VotingResultRepository votingResultRepository) {
            return new FirstServedLimitedVoteStrategy(votingResultRepository);
        }
    };

    abstract protected VoteStrategy createStrategy(VotingResultRepository votingResultRepository);

}
