package com.example.demo.unit.follow;

import com.example.demo.follow.repository.FollowRepository;
import com.example.demo.follow.service.FollowService;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.shop.entity.Shop;
import com.example.demo.shop.repository.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FollowServiceTest {
    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final FollowRepository followRepository = mock(FollowRepository.class);
    private final ShopRepository shopRepository = mock(ShopRepository.class);

    private final FollowService followService = new FollowService(memberRepository, followRepository, shopRepository);

    @Test
    @DisplayName("[정상 작동] 팔로우 기능 정상 작동")
    void doShopFollow() {
        // given
        Member memberLoggedIn = new Member();
        Long shopId = 1L;

        when(shopRepository.findById(any()))
                .thenReturn(Optional.of(new Shop()));

        // when
        followService.toggleShopFollow(memberLoggedIn, shopId);

        // then
        verify(followRepository, times(1))
                .save(any());
    }

    @Test
    @DisplayName("[비정상 작동] 존재하지 않는 상점 팔로우")
    void doShopFollowOccurErrorWhenShopWasNotExisted() {
        // given
        Member memberLoggedIn = new Member();
        Long shopId = 1L;

        when(shopRepository.findById(any()))
                .thenReturn(Optional.empty());

        // when
        Executable func = () -> followService.toggleShopFollow(memberLoggedIn, shopId);

        // then
        assertThrows(IllegalArgumentException.class, func);
    }
}