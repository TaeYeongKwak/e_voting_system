package com.gabia.voting.client.dto;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.Role;
import com.gabia.voting.client.type.ClientType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SaveClientDTO {

    private String clientId;
    private String password;
    private String clientName;
    private ClientType clientType;
    private Integer votingRightCount;

    public Client toEntity(){
        Role defaultRole = new Role("ROLE_USER");
        Role typeRole = new Role(clientType.name());

        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        roles.add(typeRole);

        return Client.builder()
                .clientId(clientId)
                .password(password)
                .clientName(clientName)
                .clientType(clientType)
                .clientRole(roles)
                .build();
    }

}
