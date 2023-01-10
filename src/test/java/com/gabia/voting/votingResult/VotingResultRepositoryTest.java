package com.gabia.voting.votingResult;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.repository.VotingResultRepository;
import com.gabia.voting.votingResult.type.OpinionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VotingResultRepositoryTest {

    @Autowired
    private VotingResultRepository votingResultRepository;

    private VotingRight votingRight;
    private Vote vote;
    private VotingResult votingResult;

    @BeforeEach
    public void setUp(){
        Client client = Client.builder()
                .clientPk(1L)
                .clientId("testId")
                .password("testPassword")
                .clientName("testUser")
                .build();

        votingRight = new VotingRight(1L, client, 10);

        Item item = Item.builder()
                .itemPk(1L)
                .itemTitle("testItem")
                .itemContent("testContent")
                .build();

        vote = Vote.builder()
                .votePk(1L)
                .item(item)
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(LocalDateTime.now().plusDays(1))
                .build();

        votingResult = VotingResult.builder()
//                .votingRight(votingRight)
//                .vote(vote)
                .count(10)
                .opinion(OpinionType.AGREEMENT)
                .build();
    }

    @Test
    public void save_test(){
        // given
        Long votePk = vote.getVotePk();
        Long votingRightPk = votingRight.getVotingRightPk();

        // when
        VotingResult saveVotingResult = votingResultRepository.save(votingResult);

        // then
        assertThat(saveVotingResult.getVotingResultPk()).isNotNull();
//        assertThat(saveVotingResult.getVote().getVotePk()).isEqualTo(votePk);
//        assertThat(saveVotingResult.getVotingRight().getVotingRightPk()).isEqualTo(votingRightPk);
    }


}
