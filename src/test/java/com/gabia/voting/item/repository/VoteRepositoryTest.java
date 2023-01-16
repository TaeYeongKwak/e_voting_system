package com.gabia.voting.item.repository;

import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.type.VoteType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test"})
@DataJpaTest
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private ItemRepository itemRepository;

    private Item item;
    private Vote vote;

    @BeforeEach
    public void setUp(){
        item = Item.builder()
                .itemTitle("testItem")
                .itemContent("testContent")
                .build();
    }

    @Test
    public void save_test(){
        // given
        Item saveItem = itemRepository.save(item);
        Long itemPk = saveItem.getItemPk();

        vote = Vote.builder()
                .item(item)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(5))
                .voteType(VoteType.UNLIMITED)
                .build();

        // when
        Vote saveVote = voteRepository.save(vote);

        // then
        assertThat(saveVote.getItem().getItemPk()).isEqualTo(itemPk);
    }

    @Test
    public void findVoteByItem_test(){
        // given
        Item saveItem = itemRepository.save(item);

        vote = Vote.builder()
                .item(item)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(5))
                .voteType(VoteType.UNLIMITED)
                .build();

        Vote saveVote = voteRepository.save(vote);
        Long votePk = saveVote.getVotePk();

        // when
        Vote findVote = voteRepository.findVoteByItem(saveItem).get();

        // then
        assertThat(findVote.getVotePk()).isEqualTo(votePk);
    }


}
