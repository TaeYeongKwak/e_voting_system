package com.gabia.voting.client.service;

import com.gabia.voting.client.dto.SaveClientDTO;
import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.client.exception.DuplicationClientIdException;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.repository.VotingRightRepository;
import com.gabia.voting.client.type.ClientType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final VotingRightRepository votingRightRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public boolean registryClient(SaveClientDTO saveClientDTO) {
        if (clientRepository.existsClientByClientId(saveClientDTO.getClientId()))
            throw new DuplicationClientIdException();

        Client saveClient = saveClientDTO.toEntity();
        saveClient.encodePassword(passwordEncoder.encode(saveClient.getPassword()));
        saveClient = clientRepository.save(saveClient);

        if (saveClientDTO.getClientType() == ClientType.SHAREHOLDER)
            votingRightRepository.save(new VotingRight(saveClient, saveClientDTO.getVotingRightCount()));

        return saveClient.getClientPk() != null;
    }
}
