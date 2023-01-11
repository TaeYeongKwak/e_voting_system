package com.gabia.voting.item.service;

import com.gabia.voting.item.dto.*;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.exception.InputErrorException;
import com.gabia.voting.item.exception.ItemNotFoundException;
import com.gabia.voting.item.exception.VoteNotFoundException;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final VoteRepository voteRepository;

    @Override
    public boolean registryItem(SaveItemDTO saveItemDTO) {
        Item saveItem = saveItemDTO.toEntity();
        saveItem = itemRepository.save(saveItem);
        return saveItem.getItemPk() != null;
    }

    @Transactional
    @Override
    public void deleteItem(Long itemPk) {
        Item deleteItem = itemRepository.findById(itemPk).orElseThrow(ItemNotFoundException::new);
        itemRepository.delete(deleteItem);
    }

    @Override
    public List<SimpleItemInfoDTO> getSimpleItemList() {
        List<Item> itemList = itemRepository.findAllByOrderByCreatedTimeDesc();
        return itemList.stream().map(SimpleItemInfoDTO::of).collect(Collectors.toList());
    }

    @Override
    public DetailItemInfoDTO getDetailItemInfo(Long itemPk) {
        Item item = itemRepository.findById(itemPk).orElseThrow(ItemNotFoundException::new);
        return DetailItemInfoDTO.of(item);
    }

    @Transactional
    @Override
    public void postVote(Long itemPk, SaveVoteDTO saveVoteDTO) {
        Item item = itemRepository.findById(itemPk).orElseThrow(ItemNotFoundException::new);
        Vote saveVote = saveVoteDTO.toEntity(item);
        voteRepository.save(saveVote);
    }

    @Transactional
    @Override
    public void modifyVote(Long itemPk, ModifyVoteDTO modifyVoteDTO) {
        Item item = itemRepository.findById(itemPk).orElseThrow(ItemNotFoundException::new);
        Vote modifyVote = voteRepository.findVoteByItem(item).orElseThrow(VoteNotFoundException::new);
        modifyVote.modifyVoteTime(modifyVoteDTO.getStartTime(), modifyVoteDTO.getEndTime());
    }

}
