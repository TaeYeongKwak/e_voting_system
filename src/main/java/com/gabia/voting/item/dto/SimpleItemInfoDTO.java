package com.gabia.voting.item.dto;

import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
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
        Vote vote = item.getVote();
        boolean canVoting = (vote != null && vote.isActivation());

        return SimpleItemInfoDTO.builder()
                .itemPk(item.getItemPk())
                .itemTitle(item.getItemTitle())
                .canVoting(canVoting)
                .createdTime(item.getCreatedTime())
                .build();
    }
}
