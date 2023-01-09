package com.gabia.voting.item.repository;

import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

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
                .createdTime(LocalDateTime.now())
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
                .build();

        // when
        Vote saveVote = voteRepository.save(vote);

        // then
        assertThat(saveVote.getItem().getItemPk()).isEqualTo(itemPk);
    }


}
