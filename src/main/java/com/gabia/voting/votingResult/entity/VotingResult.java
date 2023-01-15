package com.gabia.voting.votingResult.entity;

import com.gabia.voting.client.entity.VotingRight;
import com.gabia.voting.global.entity.BaseTimeEntity;
import com.gabia.voting.item.entity.Vote;
import com.gabia.voting.votingResult.type.OpinionType;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "voting_result")
public class VotingResult extends BaseTimeEntity {

    @Id
    @Column(name = "voting_result_pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long votingResultPk;

    @ManyToOne
    @JoinColumn(name = "voting_right_pk")
    private VotingRight votingRight;

    @ManyToOne
    @JoinColumn(name = "vote_pk")
    private Vote vote;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "opinion", nullable = false)
    @Enumerated(EnumType.STRING)
    private OpinionType opinion;
}
