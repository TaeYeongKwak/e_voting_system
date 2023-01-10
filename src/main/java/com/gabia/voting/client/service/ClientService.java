package com.gabia.voting.client.service;

import com.gabia.voting.client.dto.LoginRequestDTO;
import com.gabia.voting.client.dto.SaveClientDTO;

public interface ClientService {
    public boolean registryClient(SaveClientDTO saveClientDTO);
    public String login(LoginRequestDTO loginRequestDTO);
}
