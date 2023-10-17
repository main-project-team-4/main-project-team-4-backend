package com.example.demo.follow.dto;

import com.example.demo.config.ParameterNameConfig;
import com.example.demo.member.entity.Member;
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
    @Schema(description = "팔로우한 회원의 닉네임", example = "user1")
    @JsonProperty(ParameterNameConfig.Member.NICKNAME)
    private String nickname;
    @Schema(description = "팔로우한 회원의 프로필 이미지", example = "https://pbs.twimg.com/media/FEh5WX6aQAAXrlg.jpg")
    @JsonProperty(ParameterNameConfig.Member.IMAGE)
    private String imageUrl;

    public FollowMemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.nickname = entity.getNickname();
        this.imageUrl = Optional.of(entity)
                .map(Member::getImage)
                .map(Object::toString)
                .orElse(null);
    }
}
