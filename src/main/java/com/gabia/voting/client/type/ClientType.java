package com.gabia.voting.client.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {
    ROLE_USER,
    ROLE_SHAREHOLDER,
    ROLE_MANAGER;

    @Override
    public String toString(){
        return this.name().substring(5);
    }
}
