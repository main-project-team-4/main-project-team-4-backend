package com.example.demo.follow.controller;

import com.example.demo.follow.dto.FollowMemberResponseDto;
import com.example.demo.follow.dto.FollowResponseDto;
import com.example.demo.follow.service.FollowService;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/api/members/{memberId}/followers")
    public ResponseEntity<List<FollowMemberResponseDto>> readFollowersByMemberId(
            @PathVariable Long memberId
    ) {
        return followService.readFollowersByMemberId(memberId);
    }

    @GetMapping("/api/members/{memberId}/followings")
    public ResponseEntity<List<FollowMemberResponseDto>> readFollowingsByMemberId(
            @PathVariable Long memberId
    ) {
        return followService.readFollowingsByMemberId(memberId);
    }

    @GetMapping("/api/mypages/followerlists")
    public ResponseEntity<List<FollowMemberResponseDto>> readFollowerListInMyPage(
            @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        return followService.readFollowingsByMemberId(principal.getMember().getId());
    }

    @GetMapping("/api/shops/{shop_id}/followers")
    public ResponseEntity<List<FollowMemberResponseDto>> readFollowerListByShopId(
            @PathVariable("shop_id") Long shopId, @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        return followService.readFollowersByShopId(shopId, principal.getMember().getId());
    }


}
