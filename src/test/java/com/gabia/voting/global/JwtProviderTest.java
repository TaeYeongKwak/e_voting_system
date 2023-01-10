package com.gabia.voting.global;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.Role;
import com.gabia.voting.client.type.ClientType;
import com.gabia.voting.global.config.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtProviderTest {

    private String testKey = "9D41E9137FCD946BE63A45FD2CBEC91669D73D1144473";
    private long testTokenValidMillisecond = 3600000;

    private JwtProvider jwtProvider;
    private Client client;

    @BeforeEach
    public void setUp(){
        jwtProvider = new JwtProvider(testKey, testTokenValidMillisecond);

        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        roles.add(new Role(ClientType.ROLE_SHAREHOLDER.toString()));

        client = Client.builder()
                .clientPk(1L)
                .clientId("testId")
                .password("testPassword")
                .clientName("testUser")
                .clientRole(roles)
                .build();
    }

    @Test
    public void createToken_test(){
        // given
        Long clientPk = client.getClientPk();
        Set<Role> roles = client.getClientRole();

        // when
        String token = jwtProvider.createToken(clientPk, roles);

        // then
        assertThat(token).isNotNull();
    }

    @Test
    public void validToken_success_test(){
        // given
        Long clientPk = client.getClientPk();
        Set<Role> roles = client.getClientRole();
        String token = jwtProvider.createToken(clientPk, roles);

        // when
        boolean isValid = jwtProvider.validToken(token);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void validToken_fail_test(){
        // given
        String token = "";

        // when
        boolean isValid = jwtProvider.validToken(token);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void validToken_expiration_test(){
        // given
        long testMillisecond = -1;
        jwtProvider = new JwtProvider(testKey, testMillisecond);

        Long clientPk = client.getClientPk();
        Set<Role> roles = client.getClientRole();
        String token = jwtProvider.createToken(clientPk, roles);

        // when
        boolean isValid = jwtProvider.validToken(token);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void getAuthentication_test(){
        // given
        Long clientPk = client.getClientPk();
        Set<Role> roles = client.getClientRole();
        String token = jwtProvider.createToken(clientPk, roles);

        String[] authorityNames = new String[]{"ROLE_USER", ClientType.ROLE_SHAREHOLDER.toString()};

        // when
        Authentication authentication = jwtProvider.getAuthentication(token);

        // then
        assertThat(authentication.getName()).isEqualTo(Long.toString(clientPk));
        for (GrantedAuthority authority : authentication.getAuthorities()){
            assertThat(authority.getAuthority()).isIn(authorityNames);
        }
    }

}
