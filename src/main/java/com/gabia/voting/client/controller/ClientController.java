package com.gabia.voting.client.controller;

import com.gabia.voting.client.dto.LoginRequestDTO;
import com.gabia.voting.client.dto.SaveClientDTO;
import com.gabia.voting.client.service.ClientService;
import com.gabia.voting.client.util.LoginInfoDecoder;
import com.gabia.voting.global.dto.APIResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public APIResponseDTO login(@RequestHeader(value = "Authorization") String encodingLoginInfo){
        LoginRequestDTO loginRequestDTO = LoginInfoDecoder.basicAuthDecoder(encodingLoginInfo);
        return APIResponseDTO.success(clientService.login(loginRequestDTO));
    }

}
