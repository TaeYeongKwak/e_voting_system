package com.gabia.voting.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequestDTO {

    @NotBlank(message = "사용자 아이디가 입력되지 않았습니다.")
    private String clientId;
    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    private String password;

}
