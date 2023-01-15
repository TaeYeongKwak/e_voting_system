package com.gabia.voting.votingResult.dto;

import com.gabia.voting.item.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpinionCountDTO {

    private Integer agreementCount;
    private Integer oppositionCount;
    private Integer giveUpCount;

    public static OpinionCountDTO of(Vote vote){
        return new OpinionCountDTO(
                vote.getAgreementCount(),
                vote.getOppositionCount(),
                vote.getGiveUpCount()
        );
    }

}
