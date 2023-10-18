package com.example.demo.kakao.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class KakaoUserInfoDto {
    private final Long id;
    private final String nickname;
    private final String email;

    public static KakaoUserInfoDto fromJson(JsonNode json) {
        Long id = json.get("id")
                .asLong();
        String nickname = json.get("properties").get("nickname")
                .asText();
        String email = json.get("kakao_account").get("email")
                .asText();
        return new KakaoUserInfoDto(id, nickname, email);
    }
}
