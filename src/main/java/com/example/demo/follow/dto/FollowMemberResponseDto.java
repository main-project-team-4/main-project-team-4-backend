package com.example.demo.follow.dto;

import com.example.demo.config.ParameterNameConfig;
import com.example.demo.member.entity.Member;
import com.example.demo.shop.entity.Shop;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter @AllArgsConstructor
public class FollowMemberResponseDto {
    @Schema(description = "팔로우한 회원의 id", example = "1")
    @JsonProperty(ParameterNameConfig.Member.ID)
    private Long id;
    @Schema(description = "팔로우한 회원의 상점 id", example = "1")
    @JsonProperty(ParameterNameConfig.Shop.ID)
    private Long shopId;
    @Schema(description = "팔로우한 회원의 상점 이름", example = "적절한 상점명")
    @JsonProperty(ParameterNameConfig.Shop.NAME)
    private String shopName;
    @Schema(description = "팔로우한 회원의 닉네임", example = "user1")
    @JsonProperty(ParameterNameConfig.Member.NICKNAME)
    private String nickname;
    @Schema(description = "팔로우한 회원의 프로필 이미지", example = "https://pbs.twimg.com/media/FEh5WX6aQAAXrlg.jpg")
    @JsonProperty(ParameterNameConfig.Member.IMAGE)
    private String imageUrl;

    public FollowMemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.nickname = entity.getNickname();
        this.shopId = Optional.of(entity.getShop())
                .map(Shop::getId)
                .orElse(null);
        this.shopName = Optional.of(entity.getShop())
                .map(Shop::getShopName)
                .orElse(null);
        this.imageUrl = Optional.of(entity)
                .map(Member::getImage)
                .map(Object::toString)
                .orElse(null);
    }
}
