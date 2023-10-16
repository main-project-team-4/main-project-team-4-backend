package com.example.demo.follow.repository;

import com.example.demo.follow.entity.Follow;
import com.example.demo.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByMemberAndShop_Id(Member principal, Long shopId);

    Optional<Follow> findByMemberAndShop_Id(Member principal, Long shopId);
}
