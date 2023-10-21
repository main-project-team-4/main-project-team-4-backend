package com.example.demo.follow.repository;

import com.example.demo.follow.entity.Follow;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.example.demo.follow.entity.QFollow.follow;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements BatchFollowRepository {
    private final JPAQueryFactory factory;

    @Override
    public List<Follow> findAllByShop_Id(Collection<Long> shopIdList) {
        return factory
                .select(follow)
                .from(follow)

                .where(follow.shop.id.in(shopIdList))

                .fetch();
    }
}
