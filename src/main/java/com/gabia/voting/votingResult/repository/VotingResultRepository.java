package com.gabia.voting.votingResult.repository;

import com.gabia.voting.votingResult.entity.VotingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VotingResultRepository extends JpaRepository<VotingResult, Long> {

    @Query("select sum(v.count) FROM voting_result v where v.vote.votePk = :votePk")
    public int sumCountByVoteForUpdate(Long votePk);
}
