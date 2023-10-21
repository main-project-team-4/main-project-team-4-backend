package com.example.demo.chat;

import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.member.entity.Member;
import com.example.demo.pubsub.RedisPublisher;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;

    // websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();

        // 로그인 회원 정보로 대화명 설정
        ChatRoom chatRoom = chatRoomService.findRoomByRoomId(message.getChatRoom().getRoomId());
        ChatMessage message1 = ChatMessage.createChatMessage(chatRoom, member, message.getMessage(), message.getType());

        // 채팅방 입장시에는 대화명과 메시지를 자동으로 세팅한다.
        log.info("채팅 메시지");

        if (ChatMessage.MessageType.ENTER.equals(message1.getType())) {
            //message1.setSender("[알림]");
            message1.setMessage(member.getNickname() + "님이 입장하셨습니다.");
        }
        else if (ChatMessage.MessageType.QUIT.equals(message1.getType())) {
            //message1.setSender("[알림]");
            message1.setMessage(member.getNickname() + "님이 퇴장하셨습니다.");
            chatRoomService.deleteById(message1.getChatRoom());
        }

        chatRoom.addChatMessages(message1);
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        redisPublisher.publish(chatRoomService.getTopic(message.getChatRoom().getRoomId()), message1);
    }
}