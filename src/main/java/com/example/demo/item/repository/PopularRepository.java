package com.example.demo.item.repository;

import com.example.demo.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PopularRepository {
    Page<Item> findPopularItems(Pageable pageable);
}
