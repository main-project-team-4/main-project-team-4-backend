package com.example.demo.wish.repository;

import com.example.demo.member.entity.Member;
import com.example.demo.wish.entity.Wish;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.wish.entity.QWish.wish;

@Repository
@RequiredArgsConstructor
public class WishRepositoryImpl implements WishListRepository {
    private final JPAQueryFactory factory;

    @Override
    public List<Wish> findByMemberOrderByItem_CreatedAt(Member member) {
        return factory
                .select(wish)
                .from(wish)
                .where(
                        wish.member.id.eq(member.getId())
                )
                .orderBy(wish.item.createdAt.desc())
                .fetch();
    }
}
