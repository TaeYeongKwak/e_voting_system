package com.gabia.voting.item.service;

import com.gabia.voting.item.dto.SaveItemDTO;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private SaveItemDTO saveItemDTO;
    private Item item;

    @BeforeEach
    public void setUp(){
        saveItemDTO = new SaveItemDTO("testItem", "testContent");

        item = Item.builder()
                .itemPk(1L)
                .itemTitle(saveItemDTO.getItemTitle())
                .itemContent(saveItemDTO.getItemContent())
                .build();
    }

    @Test
    public void registryItem_success_test(){
        // given
        given(itemRepository.save(any())).willReturn(item);

        // when
        boolean saveSuccess = itemService.registryItem(saveItemDTO);

        // then
        assertThat(saveSuccess).isTrue();
    }

    @Test
    public void registryItem_fail_test(){
        // given
        item = new Item();
        given(itemRepository.save(any())).willReturn(item);

        // when
        boolean saveSuccess = itemService.registryItem(saveItemDTO);

        // then
        assertThat(saveSuccess).isFalse();
    }


}
