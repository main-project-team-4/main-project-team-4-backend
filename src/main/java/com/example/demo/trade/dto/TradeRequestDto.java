package com.example.demo.trade.dto;

import com.example.demo.config.ParameterNameConfig;
import com.example.demo.trade.type.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TradeRequestDto {
    @JsonProperty(ParameterNameConfig.Member.ID)
    private Long consumerId;

    public TradeRequestDto(Long consumerId, State state) {
        this.consumerId = consumerId;
    }
}
