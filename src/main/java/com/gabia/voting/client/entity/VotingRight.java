package com.gabia.voting.client.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "voting_right")
public class VotingRight {

    @Id
    @Column(name = "client_pk")
    private Long clientPk;

    @Column(name = "count", nullable = false)
    private Integer count;

}
