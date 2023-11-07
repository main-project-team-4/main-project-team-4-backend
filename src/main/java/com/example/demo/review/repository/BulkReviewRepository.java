package com.example.demo.review.repository;

import java.util.List;

public interface BulkReviewRepository {
    List<Boolean> existsAllByItem_Id(List<Long> itemIdList);
}
