package com.example.demo.location.dto;


import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationRequestDto {
    @JsonProperty(ParameterNameConfig.Location.NAME)
    private String name;
}
