package com.gabia.voting.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailItemInfoDTO {

    private Long itemPk;
    private String itemTitle;
    private String itemContent;
    private LocalDateTime createdTime;
    private boolean canVoting;
    private VoteInfoDTO voteInfo;

    public static DetailItemInfoDTO of(Item item){
        Vote vote = item.getVote();
        boolean canVoting = (vote != null && vote.isActivation());

        return DetailItemInfoDTO.builder()
                .itemPk(item.getItemPk())
                .itemTitle(item.getItemTitle())
                .itemContent(item.getItemContent())
                .createdTime(item.getCreatedTime())
                .canVoting(canVoting)
                .voteInfo((vote == null)? null : VoteInfoDTO.of(vote))
                .build();
    }

}
