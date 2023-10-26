package com.example.demo.chat.dto;

import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.chat.entity.MessageType;
import lombok.Getter;

@Getter
public class ChatMessageRequestDto {
    private MessageType type;
    private ChatRoom chatRoom;
    private String message;
    private String token;
}
