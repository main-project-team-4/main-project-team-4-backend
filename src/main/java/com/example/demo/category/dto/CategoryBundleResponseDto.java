package com.example.demo.category.dto;

import com.example.demo.category.entity.CategoryL;
import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class CategoryBundleResponseDto {
    @JsonProperty(ParameterNameConfig.CategoryLarge.ID)
    private Long largeCategoryId;
    @JsonProperty(ParameterNameConfig.CategoryLarge.NAME)
    private String largeCategoryName;
    @JsonProperty(ParameterNameConfig.CategoryLarge.CHILDREN)
    private List<MidCategoryResponseDto> children;

    public CategoryBundleResponseDto(CategoryL categoryL) {
        this.largeCategoryId = categoryL.getId();
        this.largeCategoryName = categoryL.getName();
        this.children = categoryL.getCategoryMiddles().stream()
                .map(entity -> new MidCategoryResponseDto(categoryL, entity))
                .toList();
    }
}
