package com.gabia.voting.item.dto;

import com.gabia.voting.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaveItemDTO {

    private String itemTitle;
    private String itemContent;

    public Item toEntity(){
        return Item.builder()
                .itemTitle(itemTitle)
                .itemContent(itemContent)
                .build();
    }

}
