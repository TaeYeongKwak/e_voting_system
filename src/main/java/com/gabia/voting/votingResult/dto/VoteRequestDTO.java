package com.gabia.voting.votingResult.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.votingResult.entity.VotingResult;
import com.gabia.voting.votingResult.type.OpinionType;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class VoteRequestDTO {

    @JsonIgnore
    private VotingRight votingRight;
    @JsonIgnore
    private Vote vote;
    private Integer count;
    private OpinionType opinion;

    public void registryInfo(VotingRight votingRight, Vote vote){
        this.votingRight = votingRight;
        this.vote = vote;
    }

    public void setCount(int count){
        this.count = count;
    }

    public VotingResult toEntity(){
        return VotingResult.builder()
                .votingRight(votingRight)
                .vote(vote)
                .count(count)
                .opinion(opinion)
                .build();
    }

}
