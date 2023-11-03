package com.example.demo.chat.dto;

import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageResponseDto {
    private Long roomId;
    private String sender;
    private String message;
    private MessageType type;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.roomId = chatMessage.getRoomId();
        this.sender = chatMessage.getSender();
        this.message = chatMessage.getMessage();
        this.type = chatMessage.getType();
    }
}


