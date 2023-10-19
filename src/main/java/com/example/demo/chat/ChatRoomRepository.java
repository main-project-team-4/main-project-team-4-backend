package com.example.demo.chat;

import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.item.entity.Item;
import com.example.demo.member.entity.Member;
import com.example.demo.pubsub.RedisSubscriber;
import com.example.demo.shop.entity.Shop;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    // Optional<ChatRoom> findChatRoomById(Long id);
    Optional<ChatRoom> findById(Long id);

    Optional<ChatRoom> findByRoomId(String id);

    List<ChatRoom> findAllBySellerIdOrConsumerId(Long id_1, Long id_2);

    Optional<Item> findItemByRoomId(String id);

    List<ChatRoom> findChatRoomsByConsumer(Member consumer);
    List<ChatRoom> findChatRoomsBySeller(Member seller);

}