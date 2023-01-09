package com.gabia.voting.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "client")
public class Client {

    @Id
    @Column(name = "client_pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientPk;

    @Column(name = "client_id", length = 50, unique = true, nullable = false)
    private String clientId;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "client_name", length = 30, nullable = false)
    private String clientName;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "client_pk", referencedColumnName = "client_pk")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Role> clientRole;

    @OneToOne
    @JoinColumn(name = "votingRightPk")
    private VotingRight votingRight;
}
