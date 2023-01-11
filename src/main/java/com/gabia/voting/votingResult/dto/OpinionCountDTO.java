package com.gabia.voting.votingResult.dto;

import com.gabia.voting.votingResult.type.OpinionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpinionCountDTO {

    private OpinionType opinion;
    private long count;

}
