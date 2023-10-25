package com.example.demo.wish.repository;

import com.example.demo.member.entity.Member;
import com.example.demo.wish.entity.Wish;

import java.util.List;

public interface WishListRepository {
    List<Wish> findByMemberOrderByItem_CreatedAt(Member member);
}
