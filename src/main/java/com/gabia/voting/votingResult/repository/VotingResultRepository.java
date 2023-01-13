package com.gabia.voting.votingResult.repository;

import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.votingResult.entity.VotingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;

public interface VotingResultRepository extends JpaRepository<VotingResult, Long> {

    public List<VotingResult> findAllByVote(Vote vote);

    public boolean existsByVotingRightAndVote(VotingRight votingRight, Vote vote);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select v from vote v where v.votePk = :votePk")
    public Vote findByVotePkForUpdate(Long votePk);
}
