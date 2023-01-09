package com.gabia.voting.client.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "voting_right")
public class VotingRight {

    @Id
    @Column(name = "voting_right_pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long votingRightPk;

    @OneToOne
    @JoinColumn(name = "clientPk")
    private Client client;

    @Column(name = "count", nullable = false)
    private Integer count;

    public VotingRight(Client client, int count){
        this.client = client;
        this.count = count;
    }

}
