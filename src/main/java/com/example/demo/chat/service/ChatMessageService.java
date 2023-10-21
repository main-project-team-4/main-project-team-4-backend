package com.example.demo.chat.service;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.example.demo.chat.dto.ChatMessageRequestDto;
import com.example.demo.chat.dto.ChatMessageResponseDto;
import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.chat.repository.ChatMessageRepository;
import com.example.demo.entity.ResponseDto;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatMessageService {
    private final JwtUtil jwtUtil;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberService memberService;

    // 메세지 저장
    public ChatMessage getMessage(ChatMessageRequestDto chatMessageRequestDto){

// sender 추가
        String userId = jwtUtil.getUserIdFromToken(chatMessageRequestDto.getToken());
        String nickName = memberService.getNickNameByUserId(userId);

// time 추
        ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        ChatMessage chatMessage = ChatMessage.builder()
                .roomId(chatMessageRequestDto.getRoomId())
                .sender(nickName)
                .time(time)
                .message(chatMessageRequestDto.getMessage())
                .build();

        chatMessageRepository.save(chatMessage);

        return chatMessage;
    }

    // 이전 메세지 전송
    public ResponseDto<List<ChatMessageResponseDto>> getMessages(String roomId){
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByRoomId(roomId);

        List<ChatMessageResponseDto> chatMessageResponseDtoList = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessageList){
            chatMessageResponseDtoList.add(ChatMessageResponseDto.builder()
                    .sender(chatMessage.getSender())
                    .message(chatMessage.getMessage())
                    .time(chatMessage.getTime())
                    .build()
            );
        }
        return ResponseDto.success(chatMessageResponseDtoList);
    }
}
