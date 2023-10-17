package com.example.demo.category.dto;

import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CategoryRequestDto {
    @Schema(description = "카테고리 이름", example = "남성의류")
    @JsonProperty(ParameterNameConfig.Category.NAME)
    private String name;
}
