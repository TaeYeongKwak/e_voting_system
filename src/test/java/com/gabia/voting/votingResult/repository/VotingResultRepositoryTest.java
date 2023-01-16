package com.gabia.voting.votingResult.repository;

import com.gabia.voting.client.entity.Client;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.client.repository.ClientRepository;
import com.gabia.voting.client.repository.VotingRightRepository;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import com.gabia.voting.item.type.VoteType;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.type.OpinionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test"})
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
                .voteType(VoteType.UNLIMITED)
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
    public void findAllByVote_test(){
        // given
        votingResultRepository.save(votingResult);

        // when
        List<VotingResult> list = votingResultRepository.findAllByVote(vote);

        // then
        assertThat(list.size()).isEqualTo(1);
    }
}
