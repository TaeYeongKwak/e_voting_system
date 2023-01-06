package com.gabia.voting.client.service;

import com.gabia.voting.client.dto.SaveClientDTO;
import com.gabia.voting.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveClient(SaveClientDTO saveClientDTO) {

    }
}
