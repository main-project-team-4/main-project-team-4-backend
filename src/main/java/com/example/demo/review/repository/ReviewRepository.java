package com.example.demo.review.repository;

import com.example.demo.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
    Page<Review> findByShop_Id(Long shopId, Pageable pageable);
}
