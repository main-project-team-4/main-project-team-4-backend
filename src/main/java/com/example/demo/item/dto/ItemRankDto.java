package com.example.demo.item.dto;

import com.example.demo.item.entity.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@Getter
@NoArgsConstructor
public class ItemRankDto {
    @Schema(description = "상품의 id", example = "1")
    private Long id;
    @Schema(description = "상품의 이미지", example = "https://pbs.twimg.com/media/FEh5WX6aQAAXrlg.jpg")
    private URL image;
    @Schema(description = "상품의 이름", example = "아비렉스 가죽자켓")
    private String name;
    @Schema(description = "상품의 가격", example = "10000")
    private int price;

    public ItemRankDto(Item item) {
        this.id = item.getId();
        this.image = item.getMain_image();
        this.name = item.getName();
        this.price = item.getPrice();
    }
}
