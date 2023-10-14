package com.example.demo.item.repository;

import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.mapper.ItemMapper;
import com.example.demo.location.entity.Location;
import com.example.demo.member.entity.Member;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.demo.item.entity.QItem.item;
import static com.example.demo.location.entity.QItemLocation.itemLocation;
import static com.example.demo.wish.entity.QWish.wish;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements SearchRepository, PopularRepository, DistanceRepository, ShopDisplayRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Item> searchBy(
            String keyword,
            Pageable pageable
    ) {
        Predicate[] predicates = {
                keywordWhereQuery(keyword)
        };

        List<Item> result = jpaQueryFactory.select(item)
                .from(item)
                .where(predicates)

                .orderBy(QueryBuilder.extractOrder(new ItemMapper(), pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count Query
        Long count = jpaQueryFactory.select(item.count())
                .from(item)
                .where(predicates)
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    public Predicate keywordWhereQuery(String keyword) {
        return StringUtils.hasText(keyword) ? item.name.containsIgnoreCase(keyword) : null;
    }

    @Override
    public Page<Item> findPopularItems(Pageable pageable) {
        List<Item> result = jpaQueryFactory
                .select(item)
                .from(item)
                .leftJoin(wish).on(item.id.eq(wish.item.id))

                .groupBy(item.id)
                .orderBy(wish.count().desc())

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count Query
        Long count = jpaQueryFactory
                .select(item.count())
                .from(item)
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<Item> findNearbyItems(Location center, Pageable pageable) {
        List<Item> result = jpaQueryFactory
                .select(item)
                .from(item)
                .leftJoin(itemLocation).on(itemLocation.item.id.eq(item.id))

                .orderBy(
                        QueryBuilder.radius(center, itemLocation._super).asc()
                )

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count Query
        Long count = jpaQueryFactory
                .select(item.count())
                .from(item)
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<Item> findInShop(Member member, Pageable pageable) {
        List<Item> result = jpaQueryFactory
                .select(item)
                .from(item)
                .where(item.shop.member.id.eq(member.getId()))

                .orderBy(
                        QueryBuilder.extractOrder(new ItemMapper(), pageable)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count Query
        Long count = jpaQueryFactory
                .select(item.count())
                .from(item)
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }
}
