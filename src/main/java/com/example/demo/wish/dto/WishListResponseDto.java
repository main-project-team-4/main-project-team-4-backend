package com.example.demo.wish.dto;

import com.example.demo.config.ParameterNameConfig;
import com.example.demo.item.entity.Item;
import com.example.demo.wish.entity.Wish;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter @AllArgsConstructor
public class WishListResponseDto {
    @Schema(description = "상품 이미지 url", example = "https://m.hoopbro.co.kr/web/product/big/202308/68034e9c48fe22a0aab33bb52b9b0f4c.jpg")
    @JsonProperty(ParameterNameConfig.Item.MAIN_IMAGE)
    private String imageUrl;
    @Schema(description = "상품 이름", example = "아비렉스 가죽자켓")
    @JsonProperty(ParameterNameConfig.Item.NAME)
    private String itemName;
    @Schema(description = "상품 상태", example = "판매중, 판매완료")
    @JsonProperty(ParameterNameConfig.Item.STATE)
    private String state;
    @Schema(description = "상품 코멘트", example = "친칠라가 좋아해요!")
    @JsonProperty(ParameterNameConfig.Item.COMMENT)
    private String comment;

    public WishListResponseDto(Wish entity) {
        Optional<Item> optionalItem = Optional.of(entity).map(Wish::getItem);

        this.imageUrl = optionalItem
                .map(Item::getMain_image)
                .map(Object::toString)
                .orElse(null);
        this.itemName = optionalItem
                .map(Item::getName)
                .orElse(null);
        this.state = optionalItem
                .map(Item::getState)
                .map(Enum::name)
                .orElse(null);
        this.comment = optionalItem
                .map(Item::getComment)
                .orElse(null);
    }
}
