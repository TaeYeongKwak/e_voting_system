package com.gabia.voting.item.repository;

import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    public Optional<Vote> findVoteByItem(Item item);
}
