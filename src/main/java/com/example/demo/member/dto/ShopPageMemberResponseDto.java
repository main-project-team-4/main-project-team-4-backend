package com.example.demo.member.dto;

import com.example.demo.config.ParameterNameConfig;
import com.example.demo.member.entity.Member;
import com.example.demo.shop.entity.Shop;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Optional;

@Getter
public class ShopPageMemberResponseDto {
    @Schema(description = "회원 ID", example = "125124")
    @JsonProperty(ParameterNameConfig.Member.ID)
    private Long id;
    @Schema(description = "회원 닉네임", example = "iksadnorth")
    @JsonProperty(ParameterNameConfig.Member.NICKNAME)
    private String nickname;
    @Schema(description = "상점 이름", example = "iksadnorth 상점")
    @JsonProperty(ParameterNameConfig.Shop.NAME)
    private String shopName;
    @Schema(description = "회원의 상점 소개", example = "엄청나게 잘 작성한 상점 소개글.")
    @JsonProperty(ParameterNameConfig.Shop.INTRO)
    private String intro;
    @Schema(description = "회원 프로필 이미지 URL", example = "https://cdn.pixabay.com/photo/2020/08/03/09/51/pencil-5459686_1280.png")
    @JsonProperty(ParameterNameConfig.Member.IMAGE)
    private String image;
    @Schema(description = "회원 상점 ID", example = "64763")
    @JsonProperty(ParameterNameConfig.Shop.ID)
    private Long shopId;
    @Schema(description = "해당 회원의 팔로잉 수", example = "150")
    @JsonProperty(ParameterNameConfig.Follow.NUM_FOLLOWINGS)
    private Long followings;
    @Schema(description = "해당 회원의 팔로워 수", example = "210")
    @JsonProperty(ParameterNameConfig.Follow.NUM_FOLLOWERS)
    private Long followers;

    public ShopPageMemberResponseDto(MemberWithFollowMapper mapper) {
        Member entity = mapper.getMember();

        this.id = entity.getId();
        this.nickname = entity.getNickname();
        this.shopName = Optional.ofNullable(entity.getShop())
                .map(Shop::getShopName)
                .orElse(null);
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
