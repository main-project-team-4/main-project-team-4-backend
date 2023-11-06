package com.example.demo.item.repository;

import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.mapper.ItemMapper;
import com.example.demo.location.entity.Location;
import com.example.demo.member.entity.Member;
import com.example.demo.trade.type.State;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.demo.item.entity.QItem.item;
import static com.example.demo.location.entity.QMemberLocation.memberLocation;
import static com.example.demo.trade.entity.QTrade.trade;
import static com.example.demo.wish.entity.QWish.wish;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements
        SearchRepository, PopularRepository, DistanceRepository, ShopDisplayRepository, TransactionRepository
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Item> searchBy(
            String keyword,
            State[] stateList,
            Pageable pageable
    ) {
        Predicate[] predicates = {
                filterByState(stateList),
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

    private Predicate filterByState(State[] stateList) {
        return stateList != null && stateList.length != 0 ? item.state.in(stateList) : null;
    }

    public Predicate keywordWhereQuery(String keyword) {
        return StringUtils.hasText(keyword) ? item.name.containsIgnoreCase(keyword) : null;
    }

    @Override
    public Page<Item> findPopularItems(State[] stateList, Pageable pageable) {
        List<Item> result = jpaQueryFactory
                .select(item)
                .from(item)
                .leftJoin(wish).on(item.id.eq(wish.item.id))

                .where(
                        filterByState(stateList)
                )

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
    public Page<Item> findNearbyItems(Location center, Member member, Pageable pageable) {
        Predicate[] predicates = {
                item.shop.member.id.ne(member.getId())
        };

        List<Item> result = jpaQueryFactory
                .select(item)
                .from(item)
                .leftJoin(memberLocation).on(memberLocation.member.id.eq(item.shop.member.id))
                .where(predicates)

                .orderBy(
                        QueryBuilder.radius(center, memberLocation._super).asc()
                )

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count Query
        Long count = jpaQueryFactory
                .select(item.count())
                .from(item)
                .where(predicates)
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<Item> findInShop(Member member, Long[] exclude, Pageable pageable) {
        Predicate[] predicates = {
                item.shop.member.id.eq(member.getId()),
                exclude != null ? item.id.notIn(exclude) : null
        };

        List<Item> result = jpaQueryFactory
                .select(item)
                .from(item)
                .where(predicates)

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
                .where(predicates)
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<Item> findPurchaseItemByMember_id(Long id, State[] stateList, Pageable pageable) {
        List<Item> result = jpaQueryFactory
                .select(item)
                .from(item)
                .join(trade).on(trade.item.id.eq(item.id))

                .where(
                        filterTradeByState(stateList),
                        trade.member.id.eq(id)
                )

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
                .join(trade).on(trade.item.id.eq(item.id))

                .where(trade.member.id.eq(id))
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<Item> findSellingItemByMember_id(Long id, State[] stateList, Pageable pageable) {
        List<Item> result = jpaQueryFactory
                .select(item)
                .from(item)
                .join(trade).on(trade.item.id.eq(item.id))

                .where(
                        filterTradeByState(stateList),
                        item.shop.member.id.eq(id)
                )

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
                .join(trade).on(trade.item.id.eq(item.id))

                .where(trade.member.id.eq(id))
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    public Page<Item> findItemByMember_IdAndStateList(Long id, State[] stateList, Pageable pageable) {
        List<Item> result = jpaQueryFactory
                .select(item)
                .from(item)

                .where(
                        filterByState(stateList),
                        item.shop.member.id.eq(id)
                )

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
                .join(trade).on(trade.item.id.eq(item.id))

                .where(trade.member.id.eq(id))
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    private static BooleanExpression filterTradeByState(State[] stateList) {
        return stateList != null && stateList.length != 0 ? trade.item.state.in(stateList) : null;
    }
}
