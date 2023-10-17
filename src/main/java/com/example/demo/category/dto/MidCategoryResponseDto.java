package com.example.demo.category.dto;

import com.example.demo.category.entity.CategoryL;
import com.example.demo.category.entity.CategoryM;
import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MidCategoryResponseDto {
    @JsonProperty(ParameterNameConfig.CategoryMiddle.ID)
    private Long middleCategoryId;
    @JsonProperty(ParameterNameConfig.CategoryMiddle.NAME)
    private String middleCategoryName;
    @JsonProperty(ParameterNameConfig.CategoryLarge.ID)
    private Long parentId;
    @JsonProperty(ParameterNameConfig.CategoryLarge.NAME)
    private String parentName;

    public MidCategoryResponseDto(CategoryL categoryL, CategoryM categoryM) {
        this.middleCategoryId = categoryM.getId();
        this.middleCategoryName = categoryM.getName();
        this.parentId = categoryL.getId();
        this.parentName = categoryL.getName();
    }
}
