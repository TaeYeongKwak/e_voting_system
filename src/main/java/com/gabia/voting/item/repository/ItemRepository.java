package com.gabia.voting.item.repository;

import com.gabia.voting.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    public List<Item> findAllByOrderByCreatedTimeDesc();

}
