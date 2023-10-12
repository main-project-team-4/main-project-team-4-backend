package com.example.demo.chat;

import com.example.demo.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findChatRoomById(String id);
}
