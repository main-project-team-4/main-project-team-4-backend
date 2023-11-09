package com.example.demo.chat.dto;

import com.example.demo.category.entity.CategoryM;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.config.ParameterNameConfig;
import com.example.demo.item.entity.Item;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.net.URL;
import java.util.Optional;

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

    @JsonProperty(ParameterNameConfig.Item.ID)
    private Long itemId;

    @Schema(description = "아이템 이름", example = "아비렉스 가죽자켓")
    @JsonProperty(ParameterNameConfig.Item.NAME)
    private String itemName;

    @JsonProperty(ParameterNameConfig.Item.PRICE)
    private int itemPrice;

    @Schema(description = "로그인한 멤버 닉네임", example = "고기닭고기")
    @JsonProperty(ParameterNameConfig.ChatRoom.SENDER)
    private String sender;

    @JsonProperty(ParameterNameConfig.ChatRoom.SELLER_IMAGE)
    private URL sellerProfileImage;

    @JsonProperty(ParameterNameConfig.ChatRoom.CONSUMER_IMAGE)
    private URL consumerProfileImage;

    @JsonProperty(ParameterNameConfig.Item.MAIN_IMAGE)
    private URL itemImage;


    public ChatRoomResponseDto(ChatRoom chatRoom, Item item){
        this.roomId = chatRoom.getId();
        this.roomName = chatRoom.getRoomName();
        this.sellerName = chatRoom.getSeller().getNickname();
        this.consumerName = chatRoom.getConsumer().getNickname();
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.itemPrice = item.getPrice();
        this.itemImage = item.getMain_image();
    }

    public ChatRoomResponseDto(ChatRoom chatRoom, Member member) {
        this.roomId = chatRoom.getId();
        this.roomName = chatRoom.getRoomName();
        this.sellerName = chatRoom.getSeller().getNickname();
        this.consumerName = chatRoom.getConsumer().getNickname();
        this.sender = member.getNickname();
        this.itemName = chatRoom.getItem().getName();
        this.itemPrice = chatRoom.getItem().getPrice();
        Optional<URL> sellerImage = Optional.of(chatRoom.getSeller()).map(Member::getImage);
        this.sellerProfileImage = sellerImage.orElse(null);
        Optional<URL> consumerImage = Optional.of(chatRoom.getConsumer()).map(Member::getImage);
        this.consumerProfileImage = consumerImage.orElse(null);
        this.itemImage = chatRoom.getItem().getMain_image();
    }
}