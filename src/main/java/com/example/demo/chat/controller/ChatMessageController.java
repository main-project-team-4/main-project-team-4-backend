package com.example.demo.chat.controller;

import com.example.demo.chat.dto.ChatMessageResponseDto;
import com.example.demo.chat.service.ChatMessageService;
import com.example.demo.dto.MessageResponseDto;
import com.example.demo.entity.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // 이전 메세지 전송
    @GetMapping("/message/{chatRoomId}")
    public ResponseDto<List<ChatMessageResponseDto>> getMessages(@PathVariable String roomId){
        return chatMessageService.getMessages(roomId);
    }
}