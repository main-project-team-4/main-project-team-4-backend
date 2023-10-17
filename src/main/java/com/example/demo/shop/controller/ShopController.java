package com.example.demo.shop.controller;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.service.ItemService;
import com.example.demo.member.dto.SignupRequestDto;
import com.example.demo.member.entity.Member;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.shop.dto.ShopRequestDto;
import com.example.demo.shop.dto.ShopResponseDto;
import com.example.demo.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShopController implements ShopDocs{
    private final ShopService shopService;
    private final ItemService itemService;

    @PostMapping("/shops/create")
    public ResponseEntity<MessageResponseDto> createShop(@RequestBody SignupRequestDto requestDto, Member member){
        return shopService.createShop(requestDto, member);
    }

    @PutMapping("/shops/{shopId}")
    public ResponseEntity<MessageResponseDto> updateShop(@PathVariable("shopId") Long shopId, @RequestBody ShopRequestDto requestDto){
        return shopService.updateShop(shopId, requestDto);
    }

    @DeleteMapping("/shops/{shopId}")
    public ResponseEntity<MessageResponseDto> deleteShop(@PathVariable("shopId") Long shopId){
        return shopService.deleteShop(shopId);
    }

    // 내 상점 바로가기
    @GetMapping("/shops")
    public ShopResponseDto insertShop(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Member member = userDetails.getMember();
        return shopService.insertShop(member);
    }

    @GetMapping("/shops/{shopId}/items")
    public ResponseEntity<Page<ItemSearchResponseDto>> readItemsOfShop(
            @PathVariable Long shopId,
            @PageableDefault Pageable pageable
            ) {
        return itemService.readItemsOfShop(shopId, pageable);
    }

}
