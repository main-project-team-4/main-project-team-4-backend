package com.example.demo.item.repository;

import com.example.demo.item.entity.Item;
import com.example.demo.location.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DistanceRepository {
    Page<Item> findNearbyItems(Location location, Pageable pageable);
}
