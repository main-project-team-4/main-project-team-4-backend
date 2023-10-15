package com.example.demo.item.dto;

import com.example.demo.category.entity.CategoryL;
import com.example.demo.category.entity.CategoryM;
import com.example.demo.item.entity.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@Getter
public class ItemResponseDto {
    @Schema(description = "상품의 id", example = "1")
    public Long id;
    @Schema(description = "상품의 가게(상점) id", example = "1")
    private Long shopId;
    @Schema(description = "상품의 이름", example = "아비렉스 가죽자켓")
    private String name;
    @Schema(description = "상품의 가격", example = "10000")
    private int price;
    @Schema(description = "상품 설명", example = "친칠라들이 좋아합니다!")
    private String comment;
    @Schema(description = "상품의 메인 이미지", example = "https://m.hoopbro.co.kr/web/product/big/202308/68034e9c48fe22a0aab33bb52b9b0f4c.jpg")
    private URL main_image;
    @Schema(description = "상품의 서브 이미지들", example = "https://m.hoopbro.co.kr/web/product/big/202308/68034e9c48fe22a0aab33bb52b9b0f4c.jpg")
    private List<URL> sub_images;

    @Schema(description = "대분류 카테고리 ID", example = "1")
    @JsonProperty("large_category_id")
    private Long largeCategoryId;
    @Schema(description = "대분류 카테고리 이름", example = "여성 의류")
    @JsonProperty("large_category_name")
    private String largeCategoryName;
    @Schema(description = "중분류 카테고리 ID", example = "1")
    @JsonProperty("large_category_id")
    private Long middleCategoryId;
    @Schema(description = "중분류 카테고리 이름", example = "아우터")
    @JsonProperty("large_category_name")
    private String middleCategoryName;

    public ItemResponseDto(Item item) {
        this.id = item.getId();
        this.shopId = item.getShop().getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.comment = item.getComment();
        this.main_image = item.getMain_image();
        this.sub_images = item.getSub_images();

        Optional<CategoryM> middleCategory = Optional.of(item.getCategoryMidId());
        this.middleCategoryId = middleCategory.map(CategoryM::getId).orElse(null);
        this.middleCategoryName = middleCategory.map(CategoryM::getName).orElse(null);

        Optional<CategoryL> largeCategory = middleCategory.map(CategoryM::getParent);
        this.largeCategoryId = largeCategory.map(CategoryL::getId).orElse(null);
        this.largeCategoryName = largeCategory.map(CategoryL::getName).orElse(null);
    }
}
