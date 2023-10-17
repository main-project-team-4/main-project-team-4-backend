package com.example.demo.follow.dto;

import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class FollowResponseDto {
    @Schema(description = "로그인 유저가 해당 유저를 팔로우 했는지 여부.", example = "true")
    @JsonProperty(ParameterNameConfig.Follow.IS_FOLLOWING)
    private Boolean isFollow;

    public FollowResponseDto(Boolean isFollow) {
        this.isFollow = isFollow;
    }
}
