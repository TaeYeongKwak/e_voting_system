package com.gabia.voting.client.repository;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.client.type.ClientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VotingRightRepositoryTest {


    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VotingRightRepository votingRightRepository;

    private Client shareholder;
    private VotingRight votingRight;


    @BeforeEach
    public void setUp(){
        shareholder = Client.builder()
                .clientId("testId")
                .password("testPassword")
                .clientName("testUser")
                .clientType(ClientType.SHAREHOLDER)
                .createdTime(LocalDateTime.now())
                .build();
    }

    @Test
    public void save_test(){
        // given
        int votingRightCount = 10;
        Client saveShareholder = clientRepository.save(shareholder);
        votingRight = new VotingRight(saveShareholder, votingRightCount);

        // when
        VotingRight saveVotingRight = votingRightRepository.save(votingRight);

        // then
        assertThat(saveVotingRight.getClient().getClientPk()).isEqualTo(saveShareholder.getClientPk());
        assertThat(saveVotingRight.getCount()).isEqualTo(votingRightCount);
    }

}
