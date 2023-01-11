package com.gabia.voting.item.dto;

import com.gabia.voting.item.entity.Item;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.item.type.VoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaveVoteDTO {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private VoteType voteType;

    public Vote toEntity(Item item){
        return Vote.builder()
                .item(item)
                .startTime(startTime)
                .endTime(endTime)
                .voteType(voteType)
                .build();
    }

}
