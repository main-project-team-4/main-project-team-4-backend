package com.example.demo.follow.controller;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.follow.dto.FollowMemberResponseDto;
import com.example.demo.follow.dto.FollowResponseDto;
import com.example.demo.follow.dto.FollowersResponseDto;
import com.example.demo.follow.service.FollowService;
import com.example.demo.member.entity.Member;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FollowController implements FollowDocs {
    private final FollowService followService;

    @PostMapping("/api/shops/{shopId}/follows")
    public ResponseEntity<FollowResponseDto> toggleShopFollow(
            @PathVariable Long shopId,
            @AuthenticationPrincipal UserDetailsImpl principal
            ) {
        return followService.toggleShopFollow(principal.getMember(), shopId);
    }

    @GetMapping("/api/shops/{shopId}/follows")
    public ResponseEntity<FollowResponseDto> readFollowersByMemberId(
            @PathVariable Long shopId,
            @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        return followService.readFollowRecordAboutTarget(principal.getMember(), shopId);
    }

    @GetMapping("/api/mypages/followerlists")
    public ResponseEntity<List<FollowMemberResponseDto>> readFollowerListInMyPage(
            @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        return followService.readFollowingsByMemberId(principal.getMember().getId());
    }

    @GetMapping("/api/shops/{shopId}/followings")
    public ResponseEntity<List<FollowMemberResponseDto>> readFollowingsByMemberId(
            @PathVariable Long shopId
    ) {
        return followService.readFollowingsByShopId(shopId);
    }

    @GetMapping("/api/shops/{shop_id}/followers")
    public ResponseEntity<List<FollowersResponseDto>> readFollowerListByShopId(
            @PathVariable("shop_id") Long shopId,
            @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        Member memberLoggedIn = Optional.ofNullable(principal)
                .map(UserDetailsImpl::getMember)
                .orElse(null);
        return followService.readFollowersByShopId(shopId, memberLoggedIn);
    }

    @DeleteMapping("/api/follows/{followId}")
    public ResponseEntity<MessageResponseDto> deleteFollow(
            @PathVariable("followId") Long followId,
            @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        return followService.deleteFollowRecord(followId, principal.getMember());
    }
}
