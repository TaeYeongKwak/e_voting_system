package com.gabia.voting.client.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {
    SHAREHOLDER("ROLE_SHAREHOLDER"),
    MANAGER("ROLE_MANAGER");

    private String authorityName;
}
