package com.example.demo.chat.service;

import com.example.demo.chat.dto.ChatRoomRequestDto;
import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.repository.ChatRoomRepository;
import com.example.demo.chat.dto.ChatRoomResponseDto;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.dto.MessageResponseDto;
import com.example.demo.entity.ResponseDto;
import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.member.entity.Member;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomService {
    private final ItemRepository itemRepository;

    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final ChatRoomRepository chatRoomRepository;
    // private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;

    // 채팅방 생성
    public ChatRoomResponseDto createChatRoom(Long itemId, Member member) {
        Item item = itemRepository.findItemById(itemId);
        Long consumerId = member.getId();

        if(chatRoomRepository.findChatRoomByItemIdAndConsumerId(itemId, consumerId)==null){
            ChatRoom chatRoom = new ChatRoom(item, member);
            chatRoomRepository.save(chatRoom);
            return new ChatRoomResponseDto(chatRoom, item, member);
        }

        else{
            ChatRoom chatRoom = chatRoomRepository.findChatRoomByItemIdAndConsumerId(itemId, consumerId);
            return new ChatRoomResponseDto(chatRoom, item, member);
        }
    }

    // 채팅방 단일 조회
    public ChatRoom getRoom(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() ->
                new IllegalArgumentException("선택한 채팅방은 존재하지 않습니다.")
        );
        return chatRoom;
    }

    // 채팅방 인원 추가, 삭제
//    public ChatRoom setUser(String roomId, ChatMessage chatMessage) {
//
//        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();
//        //null값 예외처리추가
//        Status status = socketMessage.getStatus();
//
//        // token 으로 userId 추출 -----> userId 로 닉네임 추출
//        String userId = jwtUtil.getUserIdFromToken(socketMessage.getToken());
//        String userName = userService.getUserNameByUserId(userId);
//        List<String> userList = chatRoom.getUsers();
//
//        if (status.equals(JOIN) && !(userList.contains(userName))) {
//            userList.add(userName);
//
//        } else if (status.equals(LEAVE) && userList.contains(userName)) {
//            userList.remove(userName);
//
//        }
//        chatRoom.setUsers(userList);
//
//        chatRoomRedisRepository.save(chatRoom);
//
//        return chatRoom;
//    }

    // 채팅방 인원 추가, 삭제
//    public ChatRoom setUser(Long chatRoomId, SocketMessage socketMessage) {
//
//        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
////null값 예외처리추가
//        Status status = socketMessage.getStatus();
//
//// token 으로 userId 추출 -----> userId 로 닉네임 추출
//        String userId = jwtUtil.getUserIdFromToken(socketMessage.getToken());
//        String userName = userService.getUserNameByUserId(userId);
//        List<String> userList = chatRoom.getUsers();
//
//        if (status.equals(JOIN) && !(userList.contains(userName))) {
//            userList.add(userName);
//
//        } else if (status.equals(LEAVE) && userList.contains(userName)) {
//            userList.remove(userName);
//
//        }
//        chatRoom.setUsers(userList);
//
//        chatRoomRepository.save(chatRoom);
//
//        return chatRoom;
//    }
}

//-------------------------------------------------------------------------------------------------

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
//
//    // 채팅방 상세 조회
//    public ChatRoomResponseDto getChatRoomDetails(String id, Member member) {
//        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("선택한 채팅방은 존재하지 않습니다.")
//        );
//
//        Item item = chatRoom.getItem();
//
//        return new ChatRoomResponseDto(chatRoom, item, member);
//    }
//
//    // 유저별 전체 채팅방 조회
//    public List<ChatRoomResponseDto> getAllChatRooms(Long id) {
//        List<ChatRoomResponseDto> chatRoomList = chatRoomRepository.findAllBySellerIdOrConsumerId(id, id).stream().map(ChatRoomResponseDto::new).toList();
//        return chatRoomList;
//    }
