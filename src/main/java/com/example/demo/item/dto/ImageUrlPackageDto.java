package com.example.demo.item.dto;

import com.example.demo.category.entity.CategoryL;
import com.example.demo.category.entity.CategoryM;
import com.example.demo.config.ParameterNameConfig;
import com.example.demo.item.entity.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@Getter @NoArgsConstructor
public class ImageUrlPackageDto {
    @JsonProperty(ParameterNameConfig.Item.IMAGES)
    private List<String> sub_images;

    public ImageUrlPackageDto(List<String> item) {
        this.sub_images = item;
    }

}
