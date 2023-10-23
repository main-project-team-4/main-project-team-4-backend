package com.example.demo.chat.dto;

import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.MessageType;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ChatMessageResponseDto {
    private Long roomId;
    private String sender;
    private String message;
    private MessageType type;
    private ZonedDateTime time;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.roomId = chatMessage.getId();
        this.sender = chatMessage.getSender();
        this.message = chatMessage.getMessage();
        this.type = chatMessage.getType();
        this.time = chatMessage.getTime();
    }
}


