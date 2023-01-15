package com.gabia.voting.item.entity;

import com.gabia.voting.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "item")
public class Item extends BaseTimeEntity {

    @Id
    @Column(name = "item_pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemPk;

    @Column(name = "item_title", nullable = false, length = 100)
    private String itemTitle;

    @Column(name = "item_content", nullable = false)
    private String itemContent;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.REMOVE)
    private Vote vote;

    public boolean hasVote(){
        return vote != null;
    }

    public void setVote(Vote vote){
        this.vote = vote;
    }

}
