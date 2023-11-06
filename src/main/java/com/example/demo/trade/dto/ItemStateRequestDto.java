package com.example.demo.trade.dto;

import com.example.demo.config.ParameterNameConfig;
import com.example.demo.trade.type.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ItemStateRequestDto {
    @JsonProperty(ParameterNameConfig.Item.STATE)
    private State state;
    @JsonProperty(ParameterNameConfig.Item.ID)
    private Long itemId;

    public ItemStateRequestDto(Long itemId, State state) {
        this.itemId = itemId;
        this.state = state;
    }
}
