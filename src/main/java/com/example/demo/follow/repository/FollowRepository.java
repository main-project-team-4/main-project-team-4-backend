package com.example.demo.follow.repository;

import com.example.demo.follow.entity.Follow;
import com.example.demo.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>, BatchFollowRepository {
    boolean existsByMemberAndShop_Id(Member principal, Long shopId);

    Optional<Follow> findByMemberAndShop_Id(Member principal, Long shopId);
}
