//package com.example.demo.chat.redis;
//
//import com.example.demo.chat.entity.ChatMessage;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class RedisSubscriber {
//
//    private final ObjectMapper objectMapper;
//    private final SimpMessageSendingOperations messagingTemplate;
//
//    /**
//     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
//     */
////    @Override
////    public void onMessage(Message message, byte[] pattern) {
////        log.info("RedisSubscriber 들어오긴 함 try 전");
////        try {
////            log.info("RedisSubscriber 들어오긴 함");
////            // redis에서 발행된 데이터를 받아 deserialize
////            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
////            log.info("메세지 받을 준비");
////
////            // ChatMessage 객채로 맵핑
////            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
////            log.info("메세지 객체 맵핑");
////
////            // Websocket 구독자에게 채팅 메시지 Send
////            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getChatRoom(), chatMessage);
////            log.info("메세지 발송 완료");
////        } catch (Exception e) {
////            log.error(e.getMessage());
////        }
////    }
//    public void sendMessage (String publishMessage){
//        try {
//            // ChatMessage 객채로 맵핑
//            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
//            // 채팅방을 구독한 클라이언트에게 메세지 발송
//            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getChatRoom().getRoomId(), chatMessage);
//        } catch (Exception e) {
//            log.error("Exception {}", e);
//        }
//    }
//}