package com.example.demo.chat.dto;

import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.item.entity.Item;
import com.example.demo.member.entity.Member;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {
    private Long roomId;
    private String name;

    private String sellerName;
    private String consumerName;
    private String itemName;
    private Long itemId;

    private String loginMember;

    public ChatRoomResponseDto(ChatRoom chatRoom){
        this.roomId = chatRoom.getId();
        this.name = chatRoom.getRoomName();
    }

    public ChatRoomResponseDto(ChatRoom chatRoom, Item item, Member member){
        this.roomId = chatRoom.getId();
        this.name = chatRoom.getRoomName();
        this.sellerName = chatRoom.getSeller().getNickname();
        this.consumerName = chatRoom.getConsumer().getNickname();
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.loginMember = member.getNickname();
    }
}