package com.example.demo.chat.repository;

import com.example.demo.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllBySellerIdOrConsumerId(Long id_1, Long id_2);

    ChatRoom findChatRoomByItemIdAndConsumerId(Long id_1, Long id_2);

    Optional<ChatRoom> findChatRoomById(Long id);

    List<ChatRoom> findByItem_Id(Long itemId);

    int findIsOutById(Long roomId);
}