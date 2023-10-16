package com.example.demo.member.dto;

import com.example.demo.location.entity.MemberLocation;
import com.example.demo.member.entity.Member;
import com.example.demo.shop.entity.Shop;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Optional;

@Getter
public class MyPageMemberResponseDto {
    @Schema(description = "회원 ID", example = "24123")
    private Long id;
    @Schema(description = "회원 닉네임", example = "iksadnorth")
    private String nickname;
    @Schema(description = "회원 거주지", example = "서울특별시 중구 세종대로 110 서울특별시청")
    private String location;
    @Schema(description = "회원 프로필 이미지 URL", example = "https://cdn.pixabay.com/photo/2020/08/03/09/51/pencil-5459686_1280.png")
    @JsonProperty("profile_image_url")
    private String image;
    @Schema(description = "회원 상점 ID", example = "64763")
    @JsonProperty("shop_id")
    private Long shopId;

    public MyPageMemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.nickname = entity.getNickname();
        this.location = Optional.ofNullable(entity.getLocation())
                .map(MemberLocation::getName)
                .orElse(null);
        this.image = Optional.ofNullable(entity.getImage())
                .map(Object::toString)
                .orElse(null);
        this.shopId = Optional.ofNullable(entity.getShop())
                .map(Shop::getId)
                .orElse(null);
    }
}
