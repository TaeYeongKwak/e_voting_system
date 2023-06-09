package com.gabia.voting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;

@EnableAspectJAutoProxy
@EnableRetry
@EnableJpaAuditing
@SpringBootApplication
public class GShareholderVotingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GShareholderVotingSystemApplication.class, args);
    }

}
