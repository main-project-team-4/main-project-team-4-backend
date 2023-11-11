package com.example.demo.review.dto;


import com.example.demo.config.ParameterNameConfig;
import com.example.demo.item.entity.Item;
import com.example.demo.member.entity.Member;
import com.example.demo.review.entity.Review;
import com.example.demo.shop.entity.Shop;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDto {
    @Schema(description = "리뷰 id", example = "1")
    @JsonProperty(ParameterNameConfig.Review.ID)
    private Long id;
    @Schema(description = "리뷰 내용", example = "맛있어요!")
    @JsonProperty(ParameterNameConfig.Review.COMMENT)
    private String comment;
    @Schema(description = "리뷰 작성 시간", example = "2021-08-09T15:00:00")
    @JsonProperty(ParameterNameConfig.Review.CREATED_AT)
    private LocalDateTime createdAt;

    @Schema(description = "상품을 판매하는 상점 ID.", example = "1524")
    @JsonProperty(ParameterNameConfig.Shop.ID)
    private Long shopId;
    @Schema(description = "상점 이름", example = "iksadnorth 상점")
    @JsonProperty(ParameterNameConfig.Shop.NAME)
    private String shopName;

    @Schema(description = "리뷰한 상품 ID", example = "1423")
    @JsonProperty(ParameterNameConfig.Item.ID)
    private Long itemId;
    @Schema(description = "리뷰한 상품 이름", example = "아우터")
    @JsonProperty(ParameterNameConfig.Item.NAME)
    private String itemName;
    @Schema(description = "리뷰한 상품 이미지 리스트", example = "[https://cdn.pixabay.com/photo/2023/10/14/09/20/mountains-8314422_1280.png]")
    @JsonProperty(ParameterNameConfig.Item.IMAGES)
    private List<String> itemImageUrlList;

    @Schema(description = "리뷰어 ID", example = "1423")
    @JsonProperty(ParameterNameConfig.Member.ID)
    private Long reviewerId;
    @Schema(description = "리뷰어 이름", example = "iksadnorth")
    @JsonProperty(ParameterNameConfig.Member.NICKNAME)
    private String reviewerName;
    @Schema(description = "리뷰어 프로필 이미지", example = "https://cdn.pixabay.com/photo/2023/10/14/09/20/mountains-8314422_1280.png")
    @JsonProperty(ParameterNameConfig.Member.IMAGE)
    private String reviewerProfile;
    @Schema(description = "리뷰어 상점 이름", example = "iksadnorth")
    @JsonProperty(ParameterNameConfig.Shop.CONSUMER_SHOP_NAME)
    private String reviewerShopName;
    @Schema(description = "리뷰 점수", example = "3.5463")
    @JsonProperty(ParameterNameConfig.Review.RATING)
    private Double reviewRating;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();

        Optional<Shop> shop = Optional.of(review).map(Review::getShop);
        this.shopId = shop.map(Shop::getId).orElse(null);
        this.shopName = shop.map(Shop::getShopName).orElse(null);

        Optional<Item> item = Optional.of(review).map(Review::getItem);
        this.itemId = item.map(Item::getId).orElse(null);
        this.itemName = item.map(Item::getName).orElse(null);

        List<URL> urls = item.map(Item::getImageList).orElse(List.of());
        this.itemImageUrlList = urls.stream()
                .filter(Objects::nonNull)
                .map(URL::toString)
                .toList();

        Optional<Member> member = Optional.of(review).map(Review::getMember);
        this.reviewerId = member.map(Member::getId).orElse(null);
        this.reviewerName = member.map(Member::getNickname).orElse(null);
        this.reviewerProfile = member.map(Member::getImage).map(URL::toString).orElse(null);
        this.reviewerShopName = member.map(Member::getShop).map(Shop::getShopName).orElse(null);
        this.reviewRating = review.getRating();
    }

}
