package com.example.demo.chat.dto;

import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.config.ParameterNameConfig;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.net.URL;
import java.util.Optional;

@Getter
public class ChatRoomForItemResponseDto {
    @Schema(description = "채팅방 ID", example = "523")
    @JsonProperty(ParameterNameConfig.ChatRoom.ID)
    private Long chatroomId;
    @Schema(description = "구매 희망자 ID", example = "6234")
    @JsonProperty(ParameterNameConfig.Member.ID)
    private Long memberId;
    @Schema(description = "구매 희망자 닉네임", example = "적절한 상점 닉네임")
    @JsonProperty(ParameterNameConfig.Member.NICKNAME)
    private String memberNickname;
    @Schema(description = "구매 희망자 이미지 URL", example = "https://asfefds.jpg")
    @JsonProperty(ParameterNameConfig.Member.IMAGE)
    private String memberImageUrl;

    public ChatRoomForItemResponseDto(ChatRoom entity) {
        this.chatroomId = entity.getId();

        Optional<Member> consumer = Optional.of(entity.getConsumer());
        this.memberId = consumer.map(Member::getId).orElse(null);
        this.memberNickname = consumer.map(Member::getNickname).orElse(null);
        this.memberImageUrl = consumer
                .map(Member::getImage)
                .map(URL::toString)
                .orElse(null);
    }
}
