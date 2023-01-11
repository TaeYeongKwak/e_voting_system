package com.gabia.voting.item.entity;

import com.gabia.voting.item.type.VoteType;
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

    public boolean isActivation(){
        LocalDateTime now = LocalDateTime.now();
        return this.getStartTime().isBefore(now) && this.getEndTime().isAfter(now);
    }

    public void modifyVoteTime(LocalDateTime startTime, LocalDateTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
