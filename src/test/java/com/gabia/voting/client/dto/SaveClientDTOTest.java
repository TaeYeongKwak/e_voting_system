package com.gabia.voting.client.dto;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.Role;
import com.gabia.voting.client.type.ClientType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SaveClientDTOTest {

    @Test
    public void toEntity_test(){
        // given
        String defaultRoleName = "ROLE_USER";
        ClientType typeRoleName = ClientType.ROLE_SHAREHOLDER;
        String[] roleNames = {defaultRoleName, typeRoleName.name()};

        SaveClientDTO saveClientDTO = SaveClientDTO.builder()
                .clientId("testId")
                .password("testPassword")
                .clientName("testClientName")
                .clientType(ClientType.ROLE_SHAREHOLDER)
                .votingRightCount(10)
                .build();

        // when
        Client client = saveClientDTO.toEntity();

        // then
        assertThat(client.getClientId()).isEqualTo(saveClientDTO.getClientId());
        for(Role role : client.getClientRole()){
               assertThat(role.getAuthorityName()).isIn(roleNames);
        }
    }
}
