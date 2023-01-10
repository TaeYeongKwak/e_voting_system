package com.gabia.voting.item.dto;

import com.gabia.voting.item.entity.Item;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SimpleItemInfoDTO {

    private Long itemPk;
    private String itemTitle;
    private boolean canVoting;
    private LocalDateTime createdTime;

    public static SimpleItemInfoDTO of(Item item){
        boolean canVoting = (item.getVote() != null && item.getVote().getStartTime().isBefore(LocalDateTime.now()));

        return SimpleItemInfoDTO.builder()
                .itemPk(item.getItemPk())
                .itemTitle(item.getItemTitle())
                .canVoting(canVoting)
                .createdTime(item.getCreatedTime())
                .build();
    }
}
