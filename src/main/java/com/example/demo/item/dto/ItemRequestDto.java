package com.example.demo.item.dto;

import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class ItemRequestDto {

    @Schema(description = "상품의 이름", example = "아비렉스 가죽자켓")
    @NotBlank(message = "상품의 이름을 입력해 주세요")
    @Size(min = 1,max = 30,message = "상품의 이름은 1~30자로 입력해 주세요")
    @JsonProperty(ParameterNameConfig.Item.NAME)
    private String name;

    @Schema(description = "상품의 가격", example = "10000")
    @NotBlank(message = "상품의 가격을 입력해 주세요")
    @JsonProperty(ParameterNameConfig.Item.PRICE)
    private int price;

    @Schema(description = "상품 설명", example = "친칠라들이 좋아합니다!")
    @NotBlank(message = "상품 설명을 적어주세요")
    @Size(min = 1,max = 100,message = "상품의 설명은 1~100자로 입력해 주세요")
    @JsonProperty(ParameterNameConfig.Item.COMMENT)
    private String comment;

    @JsonProperty(ParameterNameConfig.Item.MAIN_IMAGE)
    private String main_image;

    @JsonProperty(ParameterNameConfig.Item.SUB_IMAGE)
    private List<String> sub_images;

    @Schema(description = "배송비 포함여부", example = "true")
    @JsonProperty(ParameterNameConfig.Item.WITH_DELIVERY_FEE)
    private Boolean isContainingDeliveryFee;

    @Schema(description = "중분류 카테고리 ID", example = "62537")
    @JsonProperty(ParameterNameConfig.CategoryMiddle.ID)
    private Long middleCategoryId;
}
