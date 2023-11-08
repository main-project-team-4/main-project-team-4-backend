package com.example.demo.review.repository;

import com.example.demo.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, Long>, BulkReviewRepository {
    Page<Review> findByShop_Id(Long shopId, Pageable pageable);
    List<Review> findByItem_Id(Long itemId);
}
