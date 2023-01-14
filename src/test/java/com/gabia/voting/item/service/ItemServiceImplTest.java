package com.gabia.voting.item.service;

import com.gabia.voting.item.dto.*;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.exception.ItemNotFoundException;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import com.gabia.voting.item.type.VoteType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


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

    @Test
    public void deleteItem_success_test(){
        // given
        Long itemPk = item.getItemPk();

        given(itemRepository.findById(any())).willReturn(Optional.of(item));

        // when
        itemService.deleteItem(itemPk);

        // then
        verify(itemRepository, times(1)).delete(any());
    }

    @Test
    public void deleteItem_fail_test(){
        // given
        Long itemPk = item.getItemPk();

        given(itemRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(itemPk));
    }

    @Test
    public void getSimpleItemList_test(){
        // given
        int testSize = 5;
        List<Item> itemList = new ArrayList<>();
        for (long i = 0; i < testSize; i++){
            itemList.add(Item.builder()
                    .itemPk(i)
                    .itemTitle(String.valueOf(i))
                    .itemContent(String.valueOf(i))
                    .vote(Vote.builder()
                            .startTime(LocalDateTime.now().minusDays(1))
                            .endTime(LocalDateTime.now().plusDays(1))
                            .build())
                    .build());
        }

        given(itemRepository.findAllByOrderByCreatedTimeDesc()).willReturn(itemList);

        // when
        List<SimpleItemInfoDTO> simpleItemList = itemService.getSimpleItemList();

        // then
        assertThat(simpleItemList.size()).isEqualTo(testSize);
        SimpleItemInfoDTO simpleItem;
        for(int i = 0; i < testSize; i++){
            simpleItem = simpleItemList.get(i);
            assertThat(simpleItem.getItemPk()).isEqualTo(i);
            assertThat(simpleItem.isCanVoting()).isTrue();
        }

    }

    @Test
    public void getDetailItemInfo_success_test(){
        // given
        Vote vote = Vote.builder()
                .votePk(1L)
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(LocalDateTime.now().plusDays(5))
                .build();

        item = Item.builder()
                .itemPk(1L)
                .vote(vote)
                .itemTitle(saveItemDTO.getItemTitle())
                .itemContent(saveItemDTO.getItemContent())
                .build();

        Long itemPk = item.getItemPk();
        Long votePk = vote.getVotePk();

        given(itemRepository.findById(any())).willReturn(Optional.of(item));

        // when
        DetailItemInfoDTO detailItemInfoDTO = itemService.getDetailItemInfo(itemPk);

        // then
        assertThat(detailItemInfoDTO.getItemPk()).isEqualTo(itemPk);
        assertThat(detailItemInfoDTO.isCanVoting()).isTrue();
        assertThat(detailItemInfoDTO.getVoteInfo().getVotePk()).isEqualTo(votePk);
    }

    @Test
    public void getDetailItemInfo_fail_test(){
        // given
        Long itemPk = -1L;
        given(itemRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThrows(ItemNotFoundException.class, () -> itemService.getDetailItemInfo(itemPk));
    }

    @Test
    public void postVote_success_test(){
        // given
        LocalDateTime now = LocalDateTime.now();
        SaveVoteDTO saveVoteDTO = new SaveVoteDTO(now.minusDays(1), now.plusDays(1), VoteType.UNLIMITED);
        Long itemPk = item.getItemPk();

        given(itemRepository.findById(any())).willReturn(Optional.of(item));
        given(voteRepository.save(any())).willReturn(saveVoteDTO.toEntity(item));

        // when
        itemService.postVote(itemPk, saveVoteDTO);

        // then
        verify(voteRepository, times(1)).save(any());
    }

    @Test
    public void postVote_fail_test(){
        // given
        Long itemPk = -1L;

        given(itemRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThrows(ItemNotFoundException.class, () -> itemService.postVote(itemPk, new SaveVoteDTO()));
        verify(voteRepository, times(0)).save(any());
    }

    @Test
    public void modifyVote_success_test(){
        // given
        Long itemPk = item.getItemPk();
        Vote vote = Vote.builder()
                .votePk(1L)
                .item(item)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(5))
                .build();

        LocalDateTime now = LocalDateTime.now();
        ModifyVoteDTO modifyVoteDTO = new ModifyVoteDTO(now, now);

        given(itemRepository.findById(any())).willReturn(Optional.of(item));
        given(voteRepository.findVoteByItem(any())).willReturn(Optional.of(vote));

        // when
        itemService.modifyVote(itemPk, modifyVoteDTO);

        // then
        assertThat(vote.getStartTime()).isEqualTo(now);
        assertThat(vote.getEndTime()).isEqualTo(now);
    }

}
