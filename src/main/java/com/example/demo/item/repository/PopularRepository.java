package com.example.demo.item.repository;

import com.example.demo.item.entity.Item;
import com.example.demo.trade.type.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PopularRepository {
    Page<Item> findPopularItems(State[] stateList, Pageable pageable);
}
