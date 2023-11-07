package com.example.demo.chat.service;

import com.example.demo.chat.dto.ChatMessageResponseDto;
import com.example.demo.chat.dto.ChatRoomForItemResponseDto;
import com.example.demo.chat.dto.ChatRoomResponseDto;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.chat.redis.RedisSubscriber;
import com.example.demo.chat.repository.ChatRoomRepository;
import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.repository.RedisRepository;
import com.example.demo.shop.entity.Shop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomService {
    private final ItemRepository itemRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RedisRepository redisRepository;

    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    // Redis
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    Map<String, ChannelTopic> topics = new HashMap<>();

    // 채팅방 생성
    public ChatRoomResponseDto createChatRoom(Long itemId, Member member) {
        Item item = itemRepository.findItemById(itemId);
        Long consumerId = member.getId();

        if (chatRoomRepository.findChatRoomByItemIdAndConsumerId(itemId, consumerId) == null) {
            ChatRoom chatRoom = new ChatRoom(item, member);
            chatRoomRepository.save(chatRoom);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                ChatRoomResponseDto responseDto = new ChatRoomResponseDto(chatRoom, item);
                String chatRoomRedis = objectMapper.writeValueAsString(responseDto);
                redisRepository.save("chatroom:item" + item.getId(), chatRoomRedis);
                return new ChatRoomResponseDto(chatRoom, item);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("객체를 String으로 변환하지 못했습니다.");
            }
        } else {
            ChatRoom chatRoom = chatRoomRepository.findChatRoomByItemIdAndConsumerId(itemId, consumerId);
            return new ChatRoomResponseDto(chatRoom, item);
        }
    }

    // 채팅방 단일 조회
    public ChatRoom getRoom(Long roomId, Member member) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(roomId).orElseThrow(() ->
                new IllegalArgumentException("선택한 채팅방은 존재하지 않습니다.")
        );

        ChannelTopic topic = topics.get(roomId.toString());
        if (topic == null) {
            topic = new ChannelTopic(roomId.toString());
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId.toString(), topic);
        }
        return chatRoom;
    }

    // 채팅방 삭제
    public List<ChatRoomResponseDto> deleteChatRoom(Long roomId, Member member){
        Long id = member.getId();
        int isOut = chatRoomRepository.findIsOutById(roomId);

        if(isOut == 0){
            List<ChatRoomResponseDto> chatRoomList = chatRoomRepository.findAllBySellerIdOrConsumerId(id, id)
                    .stream()
                    .filter(chatRoom -> !chatRoom.getId().equals(roomId))
                    .map(chatRoom -> new ChatRoomResponseDto(chatRoom, member))
                    .toList();
            return chatRoomList;
        }
        else{
            ChatRoom deleteChatRoom = chatRoomRepository.findChatRoomById(roomId).orElseThrow(() ->
                    new IllegalArgumentException("선택한 채팅방은 존재하지 않습니다.")
            );
            chatRoomRepository.delete(deleteChatRoom);

            List<ChatRoomResponseDto> chatRoomList = chatRoomRepository.findAllBySellerIdOrConsumerId(id, id)
                    .stream()
                    .map(chatRoom -> new ChatRoomResponseDto(chatRoom, member))
                    .toList();
            return chatRoomList;
        }
    }

    public ChannelTopic getTopic(Long roomId) {
        return topics.get(roomId.toString());
    }

    //유저별 전체 채팅방 조회
    public List<ChatRoomResponseDto> getAllChatRooms(Member member) {
        Long id = member.getId();

        List<ChatRoomResponseDto> chatRoomList = chatRoomRepository.findAllBySellerIdOrConsumerId(id, id)
                .stream()
                .map(chatRoom -> new ChatRoomResponseDto(chatRoom, member))
                .toList();

        return chatRoomList;
    }

    public ResponseEntity<List<ChatRoomForItemResponseDto>> readChatRoomsForItem(Long itemId, Member member) {
        validateOwnerShipOfItem(itemId, member);

        List<ChatRoomForItemResponseDto> dtoList = chatRoomRepository.findByItem_Id(itemId).stream()
                .map(ChatRoomForItemResponseDto::new)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    private void validateOwnerShipOfItem(Long itemId, Member member) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        Optional.of(item)
                .map(Item::getShop)
                .map(Shop::getMember)
                .map(Member::getId)
                .filter(id -> id.equals(member.getId()))
                .orElseThrow(() -> new IllegalArgumentException("해당 상품의 소유주만 열람할 수 있습니다."));
    }
}