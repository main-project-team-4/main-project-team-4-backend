package com.example.demo.member.dto;

import com.example.demo.member.entity.Member;
import com.example.demo.shop.entity.Shop;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Optional;

@Getter
public class ShopPageMemberResponseDto {
    @Schema(description = "회원 ID", example = "125124")
    private Long id;
    @Schema(description = "회원 닉네임", example = "iksadnorth")
    private String nickname;
    @Schema(description = "회원의 상점 소개", example = "엄청나게 잘 작성한 상점 소개글.")
    private String intro;
    @Schema(description = "회원 프로필 이미지 URL", example = "https://cdn.pixabay.com/photo/2020/08/03/09/51/pencil-5459686_1280.png")
    @JsonProperty("profile_image_url")
    private String image;
    @Schema(description = "회원 상점 ID", example = "64763")
    @JsonProperty("shop_id")
    private Long shopId;
    @Schema(description = "해당 회원의 팔로잉 수", example = "150")
    private Long followings;
    @Schema(description = "해당 회원의 팔로워 수", example = "210")
    private Long followers;

    public ShopPageMemberResponseDto(MemberWithFollowMapper mapper) {
        Member entity = mapper.getMember();

        this.id = entity.getId();
        this.nickname = entity.getNickname();
        this.intro = Optional.ofNullable(entity.getShop())
                .map(Shop::getShopIntro)
                .orElse(null);
        this.image = Optional.ofNullable(entity.getImage())
                .map(Object::toString)
                .orElse(null);
        this.shopId = Optional.ofNullable(entity.getShop())
                .map(Shop::getId)
                .orElse(null);
        this.followings = mapper.getNumOfFollowings();
        this.followers = mapper.getNumOfFollowers();
    }
}
