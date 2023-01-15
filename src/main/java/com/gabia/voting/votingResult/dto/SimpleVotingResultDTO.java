package com.gabia.voting.votingResult.dto;

import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.type.OpinionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SimpleVotingResultDTO {

    private String clientName;
    private OpinionType opinionType;
    private Integer count;
    private LocalDateTime createdTime;

    public static SimpleVotingResultDTO of(VotingResult votingResult){
        return SimpleVotingResultDTO.builder()
                .clientName(votingResult.getVotingRight().getClient().getClientName())
                .opinionType(votingResult.getOpinion())
                .count(votingResult.getCount())
                .createdTime(votingResult.getCreatedTime())
                .build();
    }


}
