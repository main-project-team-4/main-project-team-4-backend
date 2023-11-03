package com.example.demo.chat.dto;

import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.config.ParameterNameConfig;
import com.example.demo.item.entity.Item;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.net.URL;

@Getter
public class ChatRoomResponseDto {

    @Schema(description = "채팅방 아이디", example = "1")
    @JsonProperty(ParameterNameConfig.ChatRoom.ID)
    private Long roomId;

    @Schema(description = "채팅방 이름", example = "고기닭고기")
    @JsonProperty(ParameterNameConfig.ChatRoom.NAME)
    private String roomName;

    @Schema(description = "판매자 이름", example = "고기닭고기")
    @JsonProperty(ParameterNameConfig.ChatRoom.SELLER)
    private String sellerName;

    @Schema(description = "구매자 이름", example = "고기쇠고기")
    @JsonProperty(ParameterNameConfig.ChatRoom.CONSUMER)
    private String consumerName;

    @Schema(description = "아이템 이름", example = "아비렉스 가죽자켓")
    @JsonProperty(ParameterNameConfig.Item.NAME)
    private String itemName;

    @JsonProperty(ParameterNameConfig.Item.ID)
    private Long itemId;

    @Schema(description = "로그인한 멤버 닉네임", example = "고기닭고기")
    @JsonProperty(ParameterNameConfig.ChatRoom.SENDER)
    private String sender;

    @JsonProperty(ParameterNameConfig.Member.IMAGE)
    private URL profileImage;

    @JsonProperty(ParameterNameConfig.Item.MAIN_IMAGE)
    private URL itemImage;

    public ChatRoomResponseDto(ChatRoom chatRoom, Item item){
        this.roomId = chatRoom.getId();
        this.roomName = chatRoom.getRoomName();
        this.sellerName = chatRoom.getSeller().getNickname();
        this.consumerName = chatRoom.getConsumer().getNickname();
        this.itemId = item.getId();
        this.itemName = item.getName();
    }

    public ChatRoomResponseDto(ChatRoom chatRoom, Member member) {
        this.roomId = chatRoom.getId();
        this.roomName = chatRoom.getRoomName();
        this.sellerName = chatRoom.getSeller().getNickname();
        this.consumerName = chatRoom.getConsumer().getNickname();
        this.sender = member.getNickname();
        this.itemId = chatRoom.getItem().getId();
        this.profileImage = chatRoom.getConsumer().getImage();
        this.itemImage = chatRoom.getItem().getMain_image();
    }
}