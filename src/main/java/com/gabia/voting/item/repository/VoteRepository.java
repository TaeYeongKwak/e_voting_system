package com.gabia.voting.item.repository;

import com.gabia.voting.item.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
