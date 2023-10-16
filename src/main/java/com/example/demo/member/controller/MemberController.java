package com.example.demo.member.controller;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.kakao.service.KakaoService;
import com.example.demo.member.dto.LocationRequestDto;
import com.example.demo.member.dto.LoginResponseDto;
import com.example.demo.member.dto.MemberInfoRequestDto;
import com.example.demo.member.service.MemberService;
import com.example.demo.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberController implements MemberDocs{
    private final MemberService memberService;
    private final KakaoService kakaoService;

    @PutMapping("/members/me")
    public ResponseEntity<MessageResponseDto> updateMember(
            @RequestBody MemberInfoRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        return memberService.updateMember(request, principal.getMember());//token을 받아서 수정
    }

    @PutMapping("/members/me/locations")
    public ResponseEntity<MessageResponseDto> updateMemberLocations(
            @RequestBody LocationRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        return memberService.updateMemberLocation(request, principal.getMember());
    }

    @PutMapping(
            value = "/members/me/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<MessageResponseDto> updateProfileImage(
            @RequestPart("image") MultipartFile image,
            @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        return memberService.updateProfileImage(image, principal.getMember());
    }

    @DeleteMapping("/members/me")
    public ResponseEntity<MessageResponseDto> deleteMember(
            @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        return memberService.deleteMember(principal.getMember());//token을 받아서 삭제
    }

    // 카카오 소셜 로그인 테스트를 위한 URL
    // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=851d6c649ed19d32afa2743c91134e57&redirect_uri=http://localhost:8080/api/auth/kakao/callback
    @GetMapping("/kakao/callback")
    public ResponseEntity<LoginResponseDto> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        return kakaoService.kakaoLogin(code);
    }
}
