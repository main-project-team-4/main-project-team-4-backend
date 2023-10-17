package com.example.demo.dto;

import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageResponseDto {
    @JsonProperty(ParameterNameConfig.Message.MESSAGE)
    private String msg;
    @JsonProperty(ParameterNameConfig.Message.STATUS_CODE)
    private int statusCode;
}
