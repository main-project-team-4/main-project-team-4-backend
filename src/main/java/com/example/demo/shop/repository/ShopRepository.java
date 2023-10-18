package com.example.demo.shop.repository;

import com.example.demo.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByShopName(String shopName);
}
