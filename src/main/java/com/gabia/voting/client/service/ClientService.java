package com.gabia.voting.client.service;

import com.gabia.voting.client.dto.LoginRequestDTO;
import com.gabia.voting.client.dto.SaveClientDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface ClientService {
    public boolean registryClient(SaveClientDTO saveClientDTO);
    public String login(@Valid LoginRequestDTO loginRequestDTO);
}
