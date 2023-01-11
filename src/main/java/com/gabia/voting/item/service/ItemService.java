package com.gabia.voting.item.service;

import com.gabia.voting.item.dto.*;

import java.util.List;

public interface ItemService {

    public boolean registryItem(SaveItemDTO saveItemDTO);
    public void deleteItem(Long itemPk);
    public List<SimpleItemInfoDTO> getSimpleItemList();
    public DetailItemInfoDTO getDetailItemInfo(Long itemPk);
    public void postVote(Long itemPk, SaveVoteDTO saveVoteDTO);
    public void modifyVote(Long itemPk, ModifyVoteDTO modifyVoteDTO);

}
