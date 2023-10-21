package com.example.demo.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class ChatMessageResponseDto {
    private String roomId;
    private String sender;
    private String message;
    private ZonedDateTime time;
}
