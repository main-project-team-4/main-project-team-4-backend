package com.example.demo.chat.controller;

import com.example.demo.chat.dto.ChatMessageRequestDto;
import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.chat.service.ChatMessageService;
import com.example.demo.chat.service.ChatRoomService;
import com.example.demo.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    // websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
    @MessageMapping("/chat/message")
    public void message(@Payload ChatMessageRequestDto chatMessageRequestDto) {
        String roomId = chatMessageRequestDto.getRoomId();
        ChatMessage chatMessage = chatMessageService.getMessage(chatMessageRequestDto);
        simpMessageSendingOperations.convertAndSend("/sub/" + roomId + "/chat/message", chatMessage);
    }

//    @MessageMapping("/user")
//    public void receiveUser(@Payload ChatMessage chatMessage) {
//        String roomId = chatMessage.getRoomId();
//        ChatRoom chatRoom = chatRoomService.setUser(roomId, chatMessage);
//        simpMessageSendingOperations.convertAndSend("/topic/" + roomId + "/user", chatRoom.getUsers());
//    }
}
