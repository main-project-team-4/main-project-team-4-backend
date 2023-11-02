package com.example.demo.item.dto;

import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor
public class ImageResponseDto {
    @JsonProperty(ParameterNameConfig.Item.IMAGES)
    private List<String> sub_images;

    public ImageResponseDto(List<String> sub_images) {
        this.sub_images = sub_images;
    }

}
