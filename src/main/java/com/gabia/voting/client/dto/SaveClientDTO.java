package com.gabia.voting.client.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaveClientDTO {

    private String clientId;
    private String password;
    private String clientName;

}
