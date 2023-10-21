package com.example.demo.chat.repository;

import com.example.demo.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findChatRoomByConsumerId(Long id);
    // Optional<ChatRoom> findChatRoomById(Long id);
//    Optional<ChatRoom> findById(Long id);
//
//    Optional<ChatRoom> findByRoomId(String id);
//
//    List<ChatRoom> findAllBySellerIdOrConsumerId(Long id_1, Long id_2);
//
//    Optional<Item> findItemByRoomId(String id);
//
//    List<ChatRoom> findChatRoomsByConsumer(Member consumer);
//    List<ChatRoom> findChatRoomsBySeller(Member seller);
    @Query
    ChatRoom findChatRoomByItemIdAndConsumerId(Long id_1, Long id_2);

}