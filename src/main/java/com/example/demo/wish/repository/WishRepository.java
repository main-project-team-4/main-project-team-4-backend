package com.example.demo.wish.repository;

import com.example.demo.member.entity.Member;
import com.example.demo.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish,Long>, WishListRepository {
    Optional<Wish> findByMember_IdAndItem_Id(Long memberId, Long itemId);

    List<Wish> findByMember(Member member);

    boolean existsByMember_IdAndItem_Id(Long memberId, Long itemId);
}
