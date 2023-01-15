package com.gabia.voting.item.repository;

import com.gabia.voting.item.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    private Item item;

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
        String itemTitle = item.getItemTitle();

        // when
        Item saveItem = itemRepository.save(item);

        // then
        assertThat(saveItem.getItemTitle()).isEqualTo(itemTitle);
    }

    @Test
    public void deleteById_test(){
        // given
        item = Item.builder()
                .itemPk(1L)
                .itemTitle("testItem")
                .itemContent("testContent")
                .build();
        Item saveItem = itemRepository.save(item);

        // when
        itemRepository.delete(saveItem);
        Optional<Item> findItem = itemRepository.findById(saveItem.getItemPk());

        // then
        assertThat(findItem.isEmpty()).isTrue();
    }

    @Test
    public void findAllByOrderByCreatedTimeDesc_test(){
        // given
        int testSize = 5;
        List<Item> itemList = new ArrayList<>();
        for (long i = 0; i < testSize; i++){
            itemList.add(Item.builder()
                            .itemTitle(String.valueOf(i))
                            .itemContent(String.valueOf(i))
                            .build());
        }

        itemRepository.saveAll(itemList);

        // when
        List<Item> findItemList = itemRepository.findAllByOrderByCreatedTimeDesc();

        // then
        assertThat(findItemList.size()).isEqualTo(testSize);
    }

}
