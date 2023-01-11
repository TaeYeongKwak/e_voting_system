package com.gabia.voting.client.repository;

import com.gabia.voting.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    public boolean existsClientByClientId(String clientId);
    public Optional<Client> findClientByClientId(String clientId);

}
