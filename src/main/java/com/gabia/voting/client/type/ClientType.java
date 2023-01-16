package com.gabia.voting.client.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {
    ROLE_USER("USER"),
    ROLE_SHAREHOLDER("SHAREHOLDER"),
    ROLE_MANAGER("MANAGER");

    String role;
}
