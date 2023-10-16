package com.example.demo.chat;

import com.example.demo.chat.dto.ChatRoomRequestDto;
import com.example.demo.chat.dto.ChatRoomResponseDto;
import com.example.demo.chat.entity.Chat;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.pubsub.RedisSubscriber;
import com.example.demo.shop.entity.Shop;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Service
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ItemRepository itemRepository;

    // 채팅방 생성 - 아이템 상세 페이지 -> 채팅하기 버튼 누르면 실행
//    public ResponseEntity<MessageResponseDto> createChatRoom(Long itemId, Member member) {
//        Item item = itemRepository.findItemById(itemId);
//        ChatRoom chatRoom = new ChatRoom(item, member);
//
//        chatRoomRepository.save(chatRoom);
//
//        MessageResponseDto msg = new MessageResponseDto("채팅방이 생성되었습니다.", HttpStatus.OK.value());
//        return ResponseEntity.status(HttpStatus.OK).body(msg);
//    }

    // 채팅방 상세 조회
    public ChatRoomResponseDto getChatRoomDetails(String id, Member member) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 채팅방은 존재하지 않습니다.")
        );

        Item item = chatRoom.getItem();

        return new ChatRoomResponseDto(chatRoom, item, member);
    }

    // 유저별 전체 채팅방 조회
    public List<ChatRoomResponseDto> getAllChatRooms(Long id) {
        List<ChatRoomResponseDto> chatRoomList = chatRoomRepository.findAllBySellerIdOrConsumerId(id, id).stream().map(ChatRoomResponseDto::new).toList();
        return chatRoomList;
    }


    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;
    private HashOperations<String, String, String> hashOpsEnterInfo;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        hashOpsEnterInfo = redisTemplate.opsForHash();

        topics = new HashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom findRoomById(Long id) {
        ChatRoom chatRoom = (ChatRoom) chatRoomRepository.findById(id).orElseThrow();
        return chatRoom;
    }

    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */
    public ChatRoom createChatRoom(Long itemId, Member member) {
        Item item = itemRepository.findItemById(itemId);
        String name = member.getNickname() + "와 " + item.getShop();
        ChatRoom chatRoom = ChatRoom.create(name, member, item.getShop().getMember());
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null)
            topic = new ChannelTopic(roomId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(roomId, topic);
    }

    public ChannelTopic getTopic(Long roomId) {
        return topics.get(roomId);
    }

    public List<ChatRoom> getConsumerEnterRooms(Member consumer) {
        return chatRoomRepository.findChatRoomsByConsumer(consumer);
    }

    public List<ChatRoom> getShopEnterRooms(Member seller) {
        return chatRoomRepository.findChatRoomsBySeller(seller);
    }

    public void deleteById(ChatRoom chatRoom) {
        chatRoomRepository.delete(chatRoom);
    }

    /**
     * destination정보에서 roomId 추출
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    // 유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장
    public void setUserEnterInfo(String sessionId, String roomId) {
        hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);
    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    public String getUserEnterRoomId(String sessionId) {
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저 세션정보와 맵핑된 채팅방ID 삭제
    public void removeUserEnterInfo(String sessionId) {
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
    }
}
