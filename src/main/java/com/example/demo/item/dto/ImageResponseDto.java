package com.example.demo.item.dto;

import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor
public class ImageResponseDto {

    @JsonProperty(ParameterNameConfig.Item.MAIN_IMAGE)
    private String main_image;

    @JsonProperty(ParameterNameConfig.Item.IMAGES)
    private List<String> sub_images;

    public ImageResponseDto(String main_image, List<String> sub_images) {
        this.main_image = main_image;
        this.sub_images = sub_images;
    }

}
