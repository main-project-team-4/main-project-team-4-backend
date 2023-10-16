package com.example.demo.location.repository;

import com.example.demo.location.entity.ItemLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemLocationRepository extends JpaRepository<ItemLocation, Long> {
    Optional<ItemLocation> findByName(String name);
}
