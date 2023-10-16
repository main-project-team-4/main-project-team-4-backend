package com.example.demo.chat.dto;

import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.item.entity.Item;
import com.example.demo.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {

    @Schema(description = "채팅방 아이디", example = "1")
    private Long roomId;
    @Schema(description = "채팅방 이름", example = "고기닭고기")
    private String name;
    @Schema(description = "판매자 이름", example = "고기닭고기")
    private String sellerName;
    @Schema(description = "구매자 이름", example = "고기쇠고기")
    private String consumerName;
    @Schema(description = "아이템 이름", example = "아비렉스 가죽자켓")
    private String itemName;

    @Schema(description = "로그인한 멤버 닉네임", example = "고기닭고기")
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
        this.itemName = item.getName();
        this.loginMember = member.getNickname();
    }
}