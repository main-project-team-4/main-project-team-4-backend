package com.example.demo.review.entity;


import com.example.demo.entity.TimeStamp;
import com.example.demo.item.entity.Item;
import com.example.demo.member.entity.Member;
import com.example.demo.review.dto.ReviewRequestDto;
import com.example.demo.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="review")
@Getter
@Setter
@NoArgsConstructor
public class Review extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="comment", nullable = false, length = 500)
    private String comment;

    @Column(name="rating", nullable = false, length = 500)
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    public Review(ReviewRequestDto requestDto, Shop shop, Member member){
        this.comment = requestDto.getComment();
        this.shop = shop;
        this.member = member;
    }

    public void update(ReviewRequestDto requestDto){
        this.comment = requestDto.getComment();
    }
}
