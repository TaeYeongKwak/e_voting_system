package com.gabia.voting.item.service;

import com.gabia.voting.item.dto.DetailItemInfoDTO;
import com.gabia.voting.item.dto.SaveItemDTO;
import com.gabia.voting.item.dto.SimpleItemInfoDTO;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.exception.ItemNotFoundException;
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
}
