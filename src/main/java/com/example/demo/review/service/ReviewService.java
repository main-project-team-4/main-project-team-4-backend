package com.example.demo.review.service;


import com.example.demo.dto.MessageResponseDto;
import com.example.demo.member.entity.Member;
import com.example.demo.review.dto.ReviewRequestDto;
import com.example.demo.review.dto.ReviewResponseDto;
import com.example.demo.review.entity.Review;
import com.example.demo.review.repository.ReviewRepository;
import com.example.demo.shop.entity.Shop;
import com.example.demo.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;


    public ResponseEntity<MessageResponseDto> createReview(ReviewRequestDto requestDto, Member member) {
        Shop shop = findShop(requestDto.getShopId());
        Review review = new Review(requestDto, shop,member);
        reviewRepository.save(review);
        MessageResponseDto msg = new MessageResponseDto("리뷰 작성에 성공하였습니다.", 200);
        return ResponseEntity.status(200).body(msg);
    }

    @Transactional
    public ResponseEntity<MessageResponseDto> updateReview(Long reviewId, ReviewRequestDto requestDto, Member member) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다.")
        );
        if(!review.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 리뷰의 작성자가 아닙니다.");
        }
        review.update(requestDto);
        MessageResponseDto msg = new MessageResponseDto("리뷰 수정에 성공하였습니다.", 200);
        return ResponseEntity.status(200).body(msg);
    }

    public ResponseEntity<MessageResponseDto> deleteReview(Long reviewId, Member member) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다.")
        );
        if(!review.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 리뷰의 작성자가 아닙니다.");
        }
        reviewRepository.delete(review);
        MessageResponseDto msg = new MessageResponseDto("리뷰 삭제에 성공하였습니다.", 200);
        return ResponseEntity.status(200).body(msg);

    }
    private Shop findShop(Long id){
        return shopRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 Shop이 존재하지 않습니다. Post ID: " + id)
        );
    }

    @Transactional
    public ResponseEntity<Page<ReviewResponseDto>> readReviewList(Long shopId, Pageable pageable) {
        Page<ReviewResponseDto> dtoList = reviewRepository.findByShop_Id(shopId, pageable)
                .map(ReviewResponseDto::new);
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<ReviewResponseDto> readReviewOfItem(Long itemId) {
        ReviewResponseDto dto = reviewRepository.findByItem_Id(itemId)
                .map(ReviewResponseDto::new)
                .orElseGet(ReviewResponseDto::new);
        return ResponseEntity.ok(dto);
    }
}
