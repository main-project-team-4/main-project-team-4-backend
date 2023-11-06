package com.example.demo.trade.repository;

import com.example.demo.trade.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    Optional<Trade> findByMember_IdAndItem_Id(Long memberId, Long itemId);

    Optional<Trade> findByItem_Id(Long itemId);
}
