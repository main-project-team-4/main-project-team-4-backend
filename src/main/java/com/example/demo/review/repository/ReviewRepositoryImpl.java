package com.example.demo.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.demo.review.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements BulkReviewRepository {
    private final JPAQueryFactory factory;

    @Override
    public List<Boolean> existsAllByItem_Id(List<Long> itemIdList) {
        List<Long> existingIdList = factory
                .select(review.item.id)
                .from(review)
                .where(
                        review.item.id.in(itemIdList)
                )
                .fetch();

        Set<Long> existingIdSet = new HashSet<>(existingIdList);

        return itemIdList.stream()
                .map(existingIdSet::contains)
                .toList();
    }
}
