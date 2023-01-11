package com.gabia.voting.item.controller;

import com.gabia.voting.global.dto.APIResponseDTO;
import com.gabia.voting.item.dto.ModifyVoteDTO;
import com.gabia.voting.item.dto.SaveItemDTO;
import com.gabia.voting.item.dto.SaveVoteDTO;
import com.gabia.voting.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v0/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping(value = "")
    public APIResponseDTO registryItem(@RequestBody SaveItemDTO saveItemDTO){
        itemService.registryItem(saveItemDTO);
        return APIResponseDTO.success();
    }

    @DeleteMapping(value = "/{item-pk}")
    public APIResponseDTO deleteItem(@PathVariable("item-pk") Long itemPk){
        itemService.deleteItem(itemPk);
        return APIResponseDTO.success();
    }

    @GetMapping(value = "")
    public APIResponseDTO showItemList(){
        return APIResponseDTO.success(itemService.getSimpleItemList());
    }

    @GetMapping(value = "/{item-pk}")
    public APIResponseDTO showDetailItem(@PathVariable("item-pk") Long itemPk){
        return APIResponseDTO.success(itemService.getDetailItemInfo(itemPk));
    }

    @PostMapping(value = "/{item-pk}/vote")
    public APIResponseDTO postVote(@PathVariable("item-pk") Long itemPk, @Valid @RequestBody SaveVoteDTO saveVoteDTO){
        itemService.postVote(itemPk, saveVoteDTO);
        return APIResponseDTO.success();
    }

    @PutMapping(value = "/{item-pk}/vote")
    public APIResponseDTO modifyVote(@PathVariable("item-pk") Long itemPk, @Valid @RequestBody ModifyVoteDTO modifyVoteDTO){
        itemService.modifyVote(itemPk, modifyVoteDTO);
        return APIResponseDTO.success();
    }

}
