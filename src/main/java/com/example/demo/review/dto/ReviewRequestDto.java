package com.example.demo.review.dto;

import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
    @NotNull(message = "리뷰 작성 시, 리뷰를 남길 상점 ID는 필수로 입력되어야 합니다.")
    @Schema(description = "리뷰를 작성할 상품의 id", example = "1")
    @JsonProperty(ParameterNameConfig.Item.ID)
    private Long itemId;
    @Schema(description = "리뷰 내용", example = "맛있어요!")
    @NotBlank(message = "리뷰 내용을 입력해 주세요.")
    @JsonProperty(ParameterNameConfig.Review.COMMENT)
    private String comment;
    @Schema(description = "리뷰 평점", example = "5")
    @JsonProperty(ParameterNameConfig.Review.RATING)
    private Double rating;
}
