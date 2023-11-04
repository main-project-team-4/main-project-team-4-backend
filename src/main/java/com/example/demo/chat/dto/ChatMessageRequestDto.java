package com.example.demo.chat.dto;

import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.chat.entity.MessageType;
import com.example.demo.config.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ChatMessageRequestDto {

    @JsonProperty(ParameterNameConfig.ChatMessage.TYPE)
    private MessageType type;
    @JsonProperty(ParameterNameConfig.ChatRoom.ID)
    private Long chatRoomId;
    @JsonProperty(ParameterNameConfig.ChatRoom.NAME)
    private String chatRoomName;
    @JsonProperty(ParameterNameConfig.ChatMessage.CHATMESSAGE)
    private String message;
    @JsonProperty(ParameterNameConfig.ChatRoom.SENDER)
    private String senderNickname;

    public ChatMessage toEntity() {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(this.chatRoomId);
        return new ChatMessage(
                chatRoom,
                this.senderNickname,
                this.message,
                this.type
        );
    }
}
