package com.gabia.voting.votingResult;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.repository.VotingRightRepository;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
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
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private VotingRightRepository votingRightRepository;

    private VotingRight votingRight;
    private Vote vote;
    private VotingResult votingResult;

    @BeforeEach
    public void setUp(){
        Client client = Client.builder()
                .clientId("testId")
                .password("testPassword")
                .clientName("testUser")
                .build();

        client = clientRepository.save(client);
        votingRight = new VotingRight(client, 5);
        votingRight = votingRightRepository.save(votingRight);

        Item item = Item.builder()
                .itemTitle("testItem")
                .itemContent("testContent")
                .build();

        item = itemRepository.save(item);
        vote = Vote.builder()
                .item(item)
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(LocalDateTime.now().plusDays(1))
                .build();
        vote = voteRepository.save(vote);

        votingResult = VotingResult.builder()
                .votingRight(votingRight)
                .vote(vote)
                .count(5)
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
        assertThat(saveVotingResult.getVote().getVotePk()).isEqualTo(votePk);
        assertThat(saveVotingResult.getVotingRight().getVotingRightPk()).isEqualTo(votingRightPk);
    }

    @Test
    public void sumCountByVoteForUpdate_test(){
        // given
        votingResultRepository.save(votingResult);

        Client client = Client.builder()
                .clientId("testId2")
                .password("testPassword")
                .clientName("testUser2")
                .build();

        client = clientRepository.save(client);
        VotingRight votingRight = new VotingRight(client, 5);
        votingRight = votingRightRepository.save(votingRight);

        VotingResult votingResult2 = VotingResult.builder()
                .votingRight(votingRight)
                .vote(vote)
                .count(4)
                .opinion(OpinionType.AGREEMENT)
                .build();

        votingResult2 = votingResultRepository.save(votingResult2);

        int resultCount = votingResult.getCount() + votingResult2.getCount();

        // when
        int sumCount = votingResultRepository.sumCountByVoteForUpdate(vote.getVotePk());

        // then
        assertThat(sumCount).isEqualTo(resultCount);
    }


}
