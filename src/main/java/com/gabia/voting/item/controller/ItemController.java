package com.gabia.voting.item.controller;

import com.gabia.voting.global.dto.APIResponseDTO;
import com.gabia.voting.item.dto.SaveItemDTO;
import com.gabia.voting.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
