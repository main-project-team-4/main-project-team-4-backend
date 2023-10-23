package com.example.demo.chat.service;

import com.example.demo.chat.dto.ChatMessageResponseDto;
import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.chat.entity.MessageType;
import com.example.demo.chat.repository.ChatMessageRepository;
import com.example.demo.chat.repository.ChatRoomRepository;
import com.example.demo.entity.ResponseDto;
import com.example.demo.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 이전 메세지 전송
    public ResponseDto<List<ChatMessageResponseDto>> getMessages(Long roomId){
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByRoomId(roomId);
        List<ChatMessageResponseDto> chatMessageResponseDtoList = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessageList){
            ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto(chatMessage);
            chatMessageResponseDtoList.add(chatMessageResponseDto);
        }
        return ResponseDto.success(chatMessageResponseDtoList);
    }

    public ChatMessage createChatMessage(Long roomId, Member member, String message, MessageType type) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() ->
                new IllegalArgumentException("선택한 채팅방은 존재하지 않습니다.")
        );
        roomId = chatRoom.getId();
        String sender = member.getNickname();
        ChatMessage chatMessage = new ChatMessage(chatRoom, roomId, sender, message, type);
        return chatMessage;
    }
}
