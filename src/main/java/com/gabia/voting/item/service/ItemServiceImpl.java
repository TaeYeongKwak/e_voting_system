package com.gabia.voting.item.service;

import com.gabia.voting.item.dto.SaveItemDTO;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.repository.ItemRepository;
import com.gabia.voting.item.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
