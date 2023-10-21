package com.example.demo.trade.dto;

import com.example.demo.config.ParameterNameConfig;
import com.example.demo.trade.type.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TradeRequestDto {
    @JsonProperty(ParameterNameConfig.Item.STATE)
    private State state;
    @JsonProperty(ParameterNameConfig.Item.ID)
    private Long itemId;
    @JsonProperty(ParameterNameConfig.Member.ID)
    private Long consumerId;

    public TradeRequestDto(Long itemId, Long consumerId, State state) {
        this.itemId = itemId;
        this.consumerId = consumerId;
        this.state = state;
    }
}
