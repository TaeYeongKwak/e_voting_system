package com.gabia.voting.votingResult.dto;

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


}
