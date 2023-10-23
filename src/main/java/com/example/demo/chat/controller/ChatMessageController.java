package com.example.demo.chat.controller;

import com.example.demo.chat.dto.ChatMessageResponseDto;
import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.MessageType;
import com.example.demo.chat.redis.RedisPublisher;
import com.example.demo.chat.repository.ChatMessageRepository;
import com.example.demo.chat.repository.ChatRoomRepository;
import com.example.demo.chat.service.ChatMessageService;
import com.example.demo.chat.service.ChatRoomService;
import com.example.demo.entity.ResponseDto;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepository;

    // 이전 메세지 전송
    @GetMapping("/message/{roomId}")
    public ResponseDto<List<ChatMessageResponseDto>> getMessages(@PathVariable Long roomId){
        return chatMessageService.getMessages(roomId);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (MessageType.ENTER.equals(message.getType())) {
            chatRoomService.getRoom(message.getRoomId(), userDetails.getMember());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        chatMessageRepository.save(message);
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        redisPublisher.publish(chatRoomService.getTopic(message.getRoomId()), message);
    }
}