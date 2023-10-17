package com.example.demo.wish.dto;

import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class WishReadResponseDto {
    @Schema(description = "해당 게시물을 찜했는지 여부", example = "true")
    @JsonProperty(ParameterNameConfig.Wish.IS_WISHING)
    private Boolean isWished;
}
