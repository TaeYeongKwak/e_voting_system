package com.gabia.voting.item.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

}
