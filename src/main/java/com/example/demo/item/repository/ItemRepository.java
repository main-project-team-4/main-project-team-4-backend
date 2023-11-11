package com.example.demo.item.repository;

import com.example.demo.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends
        JpaRepository<Item, Long>,
        SearchRepository, PopularRepository, DistanceRepository, ShopDisplayRepository, TransactionRepository
{
    // Item의 wish 많은 기준으로 상위 20개 상품을 가져오는 쿼리
    @Query("SELECT r FROM Item r ORDER BY (select count (w) from Wish w WHERE w.item.id = r.id) DESC limit 100")
    List<Item> findTop100ByOrderByWishCountDesc();

    Item findItemById(Long id);

}
