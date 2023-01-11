package com.gabia.voting.item.dto;

import com.gabia.voting.item.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoteInfoDTO {

    private Long votePk;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static VoteInfoDTO of(Vote vote){
        return new VoteInfoDTO(vote.getVotePk(), vote.getStartTime(), vote.getEndTime());
    }

}
