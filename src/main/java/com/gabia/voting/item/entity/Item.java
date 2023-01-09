package com.gabia.voting.item.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "item")
public class Item {

    @Id
    @Column(name = "item_pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemPk;

    @Column(name = "item_title", nullable = false, length = 100)
    private String itemTitle;

    @Column(name = "item_content", nullable = false)
    private String itemContent;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "item")
    private Vote vote;

}
