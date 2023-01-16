package com.gabia.voting.client.repository;

import com.gabia.voting.client.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test"})
@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client client;


    @BeforeEach
    public void setUp(){
        client = Client.builder()
                .clientId("testId")
                .password("testPassword")
                .clientName("testUser")
                .build();
    }

    @Test
    public void existsClientByClientId_test(){
        // given
        Client saveClient = clientRepository.save(client);
        String searchIdTrue = saveClient.getClientId();
        String searchIdFalse = "testId2";

        // when
        boolean searchResultTrue = clientRepository.existsClientByClientId(searchIdTrue);
        boolean searchResultFalse = clientRepository.existsClientByClientId(searchIdFalse);

        // then
        assertThat(searchResultTrue).isTrue();
        assertThat(searchResultFalse).isFalse();
    }

    @Test
    public void save_test(){
        // given
        String clientId = client.getClientId();

        // when
        Client saveClient = clientRepository.save(client);

        // then
        assertThat(saveClient.getClientId()).isEqualTo(clientId);
    }

    @Test
    public void findClientByClientId_test(){
        // given
        String clientId = client.getClientId();
        Client saveClient = clientRepository.save(client);

        // when
        Client findClient = clientRepository.findClientByClientId(clientId).get();

        // then
        assertThat(findClient.getClientPk()).isEqualTo(saveClient.getClientPk());
    }


}
