package com.gabia.voting.global.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token-valid-millisecond}")
    private long tokenValidMillisecond;

    @Bean
    public JwtProvider jwtProvider(){
        return new JwtProvider(secretKey, tokenValidMillisecond);
    }

}
