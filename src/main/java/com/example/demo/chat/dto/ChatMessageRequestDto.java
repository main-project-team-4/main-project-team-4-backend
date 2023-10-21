package com.example.demo.chat.dto;

import com.amazonaws.services.kms.model.MessageType;
import lombok.Getter;

@Getter
public class ChatMessageRequestDto {
    private MessageType type;
    private String roomId;
    private String message;
    private String token;
}
