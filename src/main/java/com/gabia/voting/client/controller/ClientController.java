package com.gabia.voting.client.controller;

import com.gabia.voting.client.dto.LoginRequestDTO;
import com.gabia.voting.client.dto.SaveClientDTO;
import com.gabia.voting.client.service.ClientService;
import com.gabia.voting.global.dto.APIResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v0/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping(value = "")
    public APIResponseDTO signup(@Valid @RequestBody SaveClientDTO saveClientDTO){
        clientService.registryClient(saveClientDTO);
        return APIResponseDTO.success();
    }

    @PostMapping(value = "/login")
    public APIResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return APIResponseDTO.success(clientService.login(loginRequestDTO));
    }

}
