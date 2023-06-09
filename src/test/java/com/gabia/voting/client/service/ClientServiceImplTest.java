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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

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
    @Mock
    private JwtProvider jwtProvider;

    private Client client;
    private SaveClientDTO saveClientDTO;
    private VotingRight votingRight;

    @BeforeEach
    public void setUp(){
        saveClientDTO = SaveClientDTO.builder()
                .clientId("testId")
                .password("testPassowrd")
                .clientName("testClientName")
                .clientType(ClientType.ROLE_SHAREHOLDER)
                .votingRightCount(10)
                .build();

        client = Client.builder()
                .clientPk(1L)
                .clientId(saveClientDTO.getClientId())
                .password(saveClientDTO.getPassword())
                .clientName(saveClientDTO.getClientName())
                .clientType(saveClientDTO.getClientType())
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

    @Test
    public void login_success_test(){
        // given
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(client.getClientId(), client.getPassword());
        String testToken = "test_jwt";

        given(clientRepository.findClientByClientId(any())).willReturn(Optional.of(client));
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        given(jwtProvider.createToken(any(), any())).willReturn(testToken);

        // when
        String loginToken = clientService.login(loginRequestDTO);

        // then
        assertThat(loginToken).isEqualTo(testToken);
    }

    @Test
    public void login_password_mismatch_test(){
        // given
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(client.getClientId(), client.getPassword());

        given(clientRepository.findClientByClientId(any())).willReturn(Optional.of(client));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        // when & then
        assertThrows(PasswordMisMatchException.class, () -> clientService.login(loginRequestDTO));
    }

    @Test
    public void login_not_found_test(){
        // given
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(client.getClientId(), client.getPassword());

        given(clientRepository.findClientByClientId(any())).willReturn(Optional.empty());

        // when & then
        assertThrows(ClientNotFoundException.class, () -> clientService.login(loginRequestDTO));
    }
}
