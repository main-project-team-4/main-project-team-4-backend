package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>, WithFollowInfoRepository {
    Optional<Member> findByUsername(String username);

    Member findMemberByUsername(String username);

    @Query("SELECT m FROM Member m " +
            "JOIN m.followList f " +
            "JOIN f.shop s " +
            "WHERE s.member.id = :memberId")
    List<Member> findFollowersByMember_Id(Long memberId);

    @Query("SELECT m FROM Member m " +
            "JOIN m.shop s " +
            "JOIN s.follows f " +
            "WHERE f.member.id = :memberId")
    List<Member> findFollowingsByMember_Id(Long memberId);

    @Query("SELECT m FROM Member m " +
            "JOIN m.followList f " +
            "JOIN f.shop s " +
            "WHERE s.id = :shopId")
    List<Member> findFollowersByShop_Id(Long shopId);

    @Query("SELECT m FROM Member m " +
            "JOIN m.shop s " +
            "JOIN s.follows f " +
            "WHERE f.member.shop.id = :shopId")
    List<Member> findFollowingsByShop_Id(Long shopId);

    Optional<Member> findByNickname(String nickname);

    @Query("SELECT m FROM Member m WHERE m.id = :id")
    Optional<Member> findNickNameById(@Param("id") String id);
}
