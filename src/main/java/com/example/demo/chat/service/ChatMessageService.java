package com.example.demo.chat.service;

import com.example.demo.chat.dto.ChatMessageRequestDto;
import com.example.demo.chat.dto.ChatMessageResponseDto;
import com.example.demo.chat.dto.ChatRoomResponseDto;
import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.chat.entity.MessageType;
//import com.example.demo.chat.redis.RedisPublisher;
import com.example.demo.chat.repository.ChatMessageRepository;
import com.example.demo.chat.repository.ChatRoomRepository;
import com.example.demo.entity.ResponseDto;
import com.example.demo.member.entity.Member;
import com.example.demo.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatMessageService {
    private final RedisTemplate<String, ChatMessage> redisMessageTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;
    //private final RedisPublisher redisPublisher;
    private final RedisRepository redisRepository;
    private final ChannelTopic channelTopic;

    // 새 메세지 전송 및 저장
    public ChatMessageResponseDto sendMessages(ChatMessageRequestDto requestDto, Member member) {
        ChatMessage message = requestDto.toEntity();
        if (MessageType.ENTER.equals(message.getType())) {
            chatRoomService.getRoom(message.getRoomId(), member);
            // message.setMessage(message.getSender() + "님이 입장하셨습니다.");
            log.info(message.getSender()+ "님이 입장하셨습니다.");
            return null;
        }

        Long id = member.getId();
        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(message.getRoomId()).orElseThrow(() ->
                new IllegalArgumentException("선택한 채팅방은 존재하지 않습니다.")
        );

        if((id.equals(chatRoom.getSeller().getId()) && chatRoom.getIsOut() == 2) ||
           (id.equals(chatRoom.getConsumer().getId()) && chatRoom.getIsOut() == 1)){
            chatRoom.isOutUpdate(0);
        }

        chatMessageRepository.save(message);

        // 1. 직렬화
        redisMessageTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));

        // 2. redis 저장
        redisMessageTemplate.opsForList().rightPush("chat:roomId" + message.getRoomId(), message);

        // 3. expire 을 이용해서, Key 를 만료시킬 수 있음
        // redisTemplateMessage.expire(message.getRoomId(), 1, TimeUnit.MINUTES);

        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        // redisPublisher.publish(chatRoomService.getTopic(message.getRoomId()), message);
        redisMessageTemplate.convertAndSend(channelTopic.getTopic(), message);
        return new ChatMessageResponseDto(message);
    }

    // 이전 메세지 로드
    @Cacheable(value = "chatMessages", key = "#roomId")
    public List<ChatMessageResponseDto> loadMessages(Long roomId) {
        ValueOperations<String, ChatMessage> valueOperations = redisMessageTemplate.opsForValue();
        boolean exists = valueOperations.getOperations().hasKey("chat:roomId" + roomId);

        if(exists){
            Long chatMessageCount = redisMessageTemplate.opsForList().size("chat:roomId" + roomId);
            if (chatMessageCount == null) {
                chatMessageCount = 0L;
            }

            if(chatMessageCount <= 100){
                List<ChatMessageResponseDto> chatMessageResponseDtoList = chatMessageRepository.findAllByRoomId(roomId)
                        .stream()
                        .limit(100 - chatMessageCount)
                        .map(ChatMessageResponseDto::new)
                        .toList();

                List<ChatMessage> chatMessageList = redisMessageTemplate.opsForList().range("chat:roomId" + roomId, 0, chatMessageCount);
                for (ChatMessage chatMessage : chatMessageList) {
                    chatMessageResponseDtoList.add(new ChatMessageResponseDto(chatMessage));
                }
                return chatMessageResponseDtoList;
            }

            else{
                List<ChatMessage> chatMessageList = redisMessageTemplate.opsForList().range("chat:roomId" + roomId, 0, 99);
                List<ChatMessageResponseDto> chatMessageResponseDtoList = new ArrayList<>();
                for (ChatMessage chatMessage : chatMessageList) {
                    chatMessageResponseDtoList.add(new ChatMessageResponseDto(chatMessage));
                }
                return chatMessageResponseDtoList;
            }
        }
        else{
            List<ChatMessageResponseDto> chatMessageResponseDtoList = chatMessageRepository.findAllByRoomId(roomId)
                    .stream()
                    .map(ChatMessageResponseDto::new)
                    .toList();

            return chatMessageResponseDtoList;
        }
    }
}