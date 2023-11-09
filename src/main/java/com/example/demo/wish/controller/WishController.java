package com.example.demo.wish.controller;

import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.service.ItemService;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.trade.type.State;
import com.example.demo.wish.dto.TopItemResponseDto;
import com.example.demo.wish.dto.WishListResponseDto;
import com.example.demo.wish.dto.WishReadResponseDto;
import com.example.demo.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishController implements WishDocs{
    private final WishService wishService;
    private final ItemService itemService;

    @PostMapping("/api/items/{itemId}/wishes")
    public ResponseEntity<Void> toggleWish(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @PathVariable Long itemId
    ) {
        return wishService.toggle(principal.getMember(), itemId);
    }

    @GetMapping("/api/items/{itemId}/wishes")
    public ResponseEntity<WishReadResponseDto> readWishRecord(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @PathVariable Long itemId
    ) {
        return wishService.readWishRecord(principal.getMember(), itemId);
    }

    @GetMapping("/api/mypages/wishlists")
    public ResponseEntity<List<WishListResponseDto>> readMyWishLists(
            @AuthenticationPrincipal UserDetailsImpl principal
    ) {
        return wishService.readMyWishLists(principal.getMember());
    }

    @GetMapping("/api/top-items")
    public ResponseEntity<Page<TopItemResponseDto>> readPopularItems(
            @RequestParam(required = false) State[] state,
            @PageableDefault Pageable pageable
            ) {
        return itemService.readPopularItems(state, pageable);
    }
}
