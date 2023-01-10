package com.gabia.voting.client.service;

import com.gabia.voting.client.dto.LoginRequestDTO;
import com.gabia.voting.client.dto.SaveClientDTO;
import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.client.exception.ClientNotFoundException;
import com.gabia.voting.client.exception.DuplicationClientIdException;
import com.gabia.voting.client.exception.PasswordMisMatchException;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.repository.VotingRightRepository;
import com.gabia.voting.client.type.ClientType;
import com.gabia.voting.global.config.jwt.JwtProvider;
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
    private final JwtProvider jwtProvider;


    @Transactional
    @Override
    public boolean registryClient(SaveClientDTO saveClientDTO) {
        if (clientRepository.existsClientByClientId(saveClientDTO.getClientId()))
            throw new DuplicationClientIdException();

        Client saveClient = saveClientDTO.toEntity();
        saveClient.encodePassword(passwordEncoder.encode(saveClient.getPassword()));
        saveClient = clientRepository.save(saveClient);

        if (saveClientDTO.getClientType() == ClientType.ROLE_SHAREHOLDER)
            votingRightRepository.save(new VotingRight(saveClient, saveClientDTO.getVotingRightCount()));

        return saveClient.getClientPk() != null;
    }

    @Override
    public String login(LoginRequestDTO loginRequestDTO) {
        Client loginClient = clientRepository.findClientByClientId(loginRequestDTO.getClientId()).orElseThrow(ClientNotFoundException::new);
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), loginClient.getPassword()))
            throw new PasswordMisMatchException();
        return jwtProvider.createToken(loginClient.getClientPk(), loginClient.getClientRole());
    }
}
