package com.example.demo.review.dto;


import com.example.demo.item.entity.Item;
import com.example.demo.member.entity.Member;
import com.example.demo.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;


@Getter
@Setter
public class ReviewResponseDto {
    @Schema(description = "리뷰 id", example = "1")
    private Long id;
    @Schema(description = "리뷰 내용", example = "맛있어요!")
    private String comment;
    @Schema(description = "리뷰 작성 시간", example = "2021-08-09T15:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "리뷰한 상품 ID", example = "1423")
    @JsonProperty("item_id")
    private Long itemId;
    @Schema(description = "리뷰한 상품 이름", example = "아우터")
    @JsonProperty("item_name")
    private String itemName;

    @Schema(description = "리뷰어 ID", example = "1423")
    @JsonProperty("review_id")
    private Long reviewerId;
    @Schema(description = "리뷰어 이름", example = "iksadnorth")
    @JsonProperty("review_name")
    private String reviewerName;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();

        Optional<Item> item = Optional.of(review).map(Review::getItem);
        this.itemId = item.map(Item::getId).orElse(null);
        this.itemName = item.map(Item::getName).orElse(null);

        Optional<Member> member = Optional.of(review).map(Review::getMember);
        this.reviewerId = member.map(Member::getId).orElse(null);
        this.reviewerName = member.map(Member::getNickname).orElse(null);
    }

}
