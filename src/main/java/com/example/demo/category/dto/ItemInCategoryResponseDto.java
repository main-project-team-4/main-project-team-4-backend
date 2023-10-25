package com.example.demo.category.dto;

import com.example.demo.config.ParameterNameConfig;
import com.example.demo.item.entity.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter @RequiredArgsConstructor
public class ItemInCategoryResponseDto {
    @Schema(description = "상품 id", example = "1")
    @JsonProperty(ParameterNameConfig.Item.ID)
    private Long id;
    @Schema(description = "상품 이름", example = "나이키 반팔티")
    @JsonProperty(ParameterNameConfig.Item.NAME)
    private String name;
    @Schema(description = "상품 가격", example = "10000")
    @JsonProperty(ParameterNameConfig.Item.PRICE)
    private Integer price;
    @Schema(description = "상품 이미지", example = "https://images.kolonmall.com/Prod_Img/10003414/2022/LM1/K1681264588128081NO01_LM1.jpg")
    @JsonProperty(ParameterNameConfig.Item.MAIN_IMAGE)
    private String imageUrl;

    @Schema(description = "상품 상태", example = "SELLING")
    @JsonProperty(ParameterNameConfig.Item.STATE)
    private String state;


    public ItemInCategoryResponseDto(Item entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.imageUrl = Optional.of(entity)
                .map(Item::getMain_image)
                .map(Object::toString)
                .orElse(null);
        this.state = Optional.of(entity)
                .map(Item::getState)
                .map(Enum::name)
                .orElse(null);
    }
}
