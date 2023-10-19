package com.example.demo.kakao.service;

import com.example.demo.jwt.JwtUtil;
import com.example.demo.kakao.dto.KakaoUserInfoDto;
import com.example.demo.kakao.properties.KakaoOAuth2Properties;
import com.example.demo.member.dto.LoginResponseDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.security.UserRoleEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j(topic = "KAKAO Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final KakaoOAuth2Properties kakaoOAuth2Properties;
    private final ObjectMapper objectMapper;

    public ResponseEntity<LoginResponseDto> kakaoLogin(String code) throws JsonProcessingException {
        String accessToken = getToken(code);
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        Tray tray = registerKakaoUserIfNeeded(kakaoUserInfo);
        String token = jwtUtil.createToken(tray.member().getUsername(), UserRoleEnum.USER);

        LoginResponseDto response = new LoginResponseDto("로그인 성공", token, tray.first());
        return ResponseEntity.ok(response);
    }

    private String getToken(String code) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoOAuth2Properties.getClientId());
        body.add("redirect_uri", kakaoOAuth2Properties.getRedirectUrl());
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return KakaoUserInfoDto.fromJson(jsonNode);
    }

    private Tray registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        String kakaoId = String.valueOf(kakaoUserInfo.getId());
        Member kakaoUser = memberRepository.findByUsername(kakaoId).orElse(null);

        boolean first = kakaoUser == null;
        if (first) {
            String nickname = kakaoUserInfo.getNickname();
            kakaoUser = new Member(kakaoId, nickname);
            memberRepository.save(kakaoUser);
        }
        return new Tray(kakaoUser, first);
    }

    record Tray(
            Member member,
            boolean first
    ) {}
}