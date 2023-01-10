package com.gabia.voting.votingResult.repository;

import com.gabia.voting.votingResult.entity.VotingResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingResultRepository extends JpaRepository<VotingResult, Long> {
}
