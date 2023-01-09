package com.gabia.voting.client.service;

import com.gabia.voting.client.dto.SaveClientDTO;
import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.client.exception.DuplicationClientIdException;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.repository.VotingRightRepository;
import com.gabia.voting.client.type.ClientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private VotingRightRepository votingRightRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Client client;
    private SaveClientDTO saveClientDTO;
    private VotingRight votingRight;

    @BeforeEach
    public void setUp(){
        saveClientDTO = SaveClientDTO.builder()
                .clientId("testId")
                .password("testPassowrd")
                .clientName("testClientName")
                .clientType(ClientType.SHAREHOLDER)
                .votingRightCount(10)
                .build();

        client = Client.builder()
                .clientPk(1L)
                .clientId(saveClientDTO.getClientId())
                .password(saveClientDTO.getPassword())
                .clientName(saveClientDTO.getClientName())
                .clientType(ClientType.SHAREHOLDER)
                .createdTime(LocalDateTime.now())
                .build();

        votingRight = new VotingRight(1L, client, 10);
    }

    @Test
    public void registryClient_success_test(){
        // given
        String encodePassword = "[encode]testPassword";

        given(clientRepository.existsClientByClientId(any())).willReturn(false);
        given(clientRepository.save(any())).willReturn(client);
        given(passwordEncoder.encode(any())).willReturn(encodePassword);
        given(votingRightRepository.save(any())).willReturn(votingRight);

        // when
        boolean saveSuccess = clientService.registryClient(saveClientDTO);

        // then
        assertThat(saveSuccess).isTrue();
    }

    @Test
    public void registryClient_fail_test(){
        // given
        client = new Client();

        given(clientRepository.existsClientByClientId(any())).willReturn(false);
        given(clientRepository.save(any())).willReturn(client);

        // when
        boolean saveSuccess = clientService.registryClient(saveClientDTO);

        // then
        assertThat(saveSuccess).isFalse();
    }

    @Test
    public void registryClient_exception_test(){
        // given
        given(clientRepository.existsClientByClientId(any())).willReturn(true);

        // when & then
        assertThrows(DuplicationClientIdException.class, () -> clientService.registryClient(saveClientDTO));
    }
}
