package com.gabia.voting.item.entity;

import com.gabia.voting.item.type.VoteType;
import com.gabia.voting.votingResult.type.OpinionType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "vote")
public class Vote {

    @Id
    @Column(name = "vote_pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long votePk;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_pk")
    private Item item;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "vote_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @Column(name = "agreement_count")
    private Integer agreementCount;

    @Column(name = "opposition_count")
    private Integer oppositionCount;

    @Column(name = "give_up_count")
    private Integer giveUpCount;

    @Version
    private Long version;

    @PrePersist
    public void prePersist(){
        this.agreementCount = (this.agreementCount == null)? 0 : this.agreementCount;
        this.oppositionCount = (this.oppositionCount == null)? 0 : this.oppositionCount;
        this.giveUpCount = (this.giveUpCount == null)? 0 : this.giveUpCount;
    }

    public boolean isActivation(){
        LocalDateTime now = LocalDateTime.now();
        return this.getStartTime().isBefore(now) && this.getEndTime().isAfter(now);
    }

    public void modifyVoteTime(LocalDateTime startTime, LocalDateTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void updateVotingCount(OpinionType opinionType, int count){
        switch(opinionType){
            case AGREEMENT:
                this.agreementCount += count;
                break;
            case OPPOSITION:
                this.oppositionCount += count;
                break;
            case GIVE_UP:
                this.giveUpCount += count;
                break;
        }
    }

}
