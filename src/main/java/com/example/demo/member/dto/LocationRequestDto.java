package com.example.demo.member.dto;

import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class LocationRequestDto {
    @Schema(description = "바꿀 위치", example = "카카오 본사")
    @JsonProperty(ParameterNameConfig.Location.NAME)
    private String location;
}
