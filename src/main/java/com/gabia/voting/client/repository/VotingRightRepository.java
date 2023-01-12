package com.gabia.voting.client.repository;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotingRightRepository extends JpaRepository<VotingRight, Long> {

    public Optional<VotingRight> findByClient(Client client);
}
