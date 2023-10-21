package com.example.demo.chat.entity;

import com.example.demo.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MessageType type; // 메시지 타입

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    private String sender; // 메시지 보낸사람

    private String message; // 메시지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    private String roomId;

    private ZonedDateTime time;

    private String token;


    public static ChatMessage createChatMessage(ChatRoom chatRoom, String member, String message, MessageType type) {
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .roomId(chatRoom.getRoomId())
                .sender(member)
                .message(message)
                .type(type)
                .build();
        return chatMessage;
    }

    public void setSender(String sender){
        this.sender = sender;
    }

    public void setMessage(String message){
        this.message=message;
    }

    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, QUIT, TALK
    }
}