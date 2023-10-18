package com.example.demo.member.repository;

import com.example.demo.member.dto.MemberWithFollowMapper;
import com.example.demo.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.demo.follow.entity.QFollow.follow;
import static com.example.demo.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements WithFollowInfoRepository {
    private final JPAQueryFactory factory;

    @Override
    public Optional<MemberWithFollowMapper> findWithFollowInfoById(Long id) {
        Member entity = factory
                .selectFrom(member)
                .where(member.id.eq(id))
                .fetchFirst();

        long followers = factory
                .select(follow.count())
                .from(follow)
                .where(follow.shop.member.id.eq(id))
                .fetchFirst();

        long followings = factory
                .select(follow.count())
                .from(follow)
                .where(follow.member.id.eq(id))
                .fetchFirst();

        MemberWithFollowMapper result = new MemberWithFollowMapper(
                entity, followers, followings
        );
        return Optional.of(result);
    }

    @Override
    public Optional<MemberWithFollowMapper> findWithFollowInfoByShopId(Long shopId) {
        Member entity = factory
                .selectFrom(member)
                .where(member.shop.id.eq(shopId))
                .fetchFirst();

        long followers = factory
                .select(follow.count())
                .from(follow)
                .where(follow.shop.id.eq(shopId))
                .fetchFirst();

        long followings = factory
                .select(follow.count())
                .from(follow)
                .where(follow.member.shop.id.eq(shopId))
                .fetchFirst();

        MemberWithFollowMapper result = new MemberWithFollowMapper(
                entity, followers, followings
        );
        return Optional.of(result);
    }
}
