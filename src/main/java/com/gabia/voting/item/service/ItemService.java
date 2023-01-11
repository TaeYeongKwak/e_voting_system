package com.gabia.voting.item.service;

import com.gabia.voting.item.dto.DetailItemInfoDTO;
import com.gabia.voting.item.dto.SaveItemDTO;
import com.gabia.voting.item.dto.SimpleItemInfoDTO;

import java.util.List;

public interface ItemService {

    public boolean registryItem(SaveItemDTO saveItemDTO);
    public void deleteItem(Long itemPk);
    public List<SimpleItemInfoDTO> getSimpleItemList();
    public DetailItemInfoDTO getDetailItemInfo(Long itemPk);

}
