package com.example.demo.chat.dto;

import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.MessageType;
import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class ChatMessageResponseDto {
    @JsonProperty(ParameterNameConfig.ChatRoom.ID)
    private Long roomId;
    @JsonProperty(ParameterNameConfig.ChatRoom.SENDER)
    private String sender;
    @JsonProperty(ParameterNameConfig.ChatMessage.CHATMESSAGE)
    private String message;
    @JsonProperty(ParameterNameConfig.ChatMessage.TYPE)
    private MessageType type;
    @JsonProperty(ParameterNameConfig.ChatMessage.CREATED_AT)
    private LocalDateTime created_at;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.roomId = chatMessage.getRoomId();
        this.sender = chatMessage.getSender();
        this.message = chatMessage.getMessage();
        this.type = chatMessage.getType();
        this.created_at = this.getCreated_at();
    }
}