package com.example.demo.member.dto;


import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberInfoRequestDto {
    @Schema(description = "유저의 닉네임", example = "고기닭고기")
    @JsonProperty(ParameterNameConfig.Member.NICKNAME)
    private String nickname;

    @Schema(description = "유저 상점 닉네임", example = "고기닭고기 상점")
    @JsonProperty(ParameterNameConfig.Shop.NAME)
    private String shopName;

    @Schema(description = "유저 상점 소개글", example = "정말 잘 작성한 상점 소개글")
    @JsonProperty(ParameterNameConfig.Shop.INTRO)
    private String shopIntro;

}
