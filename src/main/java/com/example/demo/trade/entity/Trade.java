package com.example.demo.trade.entity;

import com.example.demo.item.entity.Item;
import com.example.demo.member.entity.Member;
import com.example.demo.shop.entity.Shop;
import com.example.demo.trade.type.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity @Table(name = "trade")
@Getter @Setter @NoArgsConstructor
public class Trade {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member sellerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "item_id")
    private Item item;

    public Trade(Member consumer, Item item, State state) {
        this.member = consumer;
        this.item = item;
        item.setState(state);
        this.sellerId = Optional.of(item.getShop())
                .map(Shop::getMember)
                .orElse(null);
    }
}
