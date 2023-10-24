package com.example.demo.item.controller;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.dto.itemRequestDto;
import com.example.demo.item.service.ItemService;
import com.example.demo.member.entity.Member;
import com.example.demo.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemController implements ItemDocs {

    private final ItemService itemService;

//    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<MessageResponseDto> createItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestParam(value = "main_image", required = false) MultipartFile main_image,
            @Valid @RequestParam(value = "sub_image", required = false) List<MultipartFile> sub_images,
            @RequestPart(value = "requestDto", required = false) itemRequestDto requestDto
            ) throws IOException {
        Member member = userDetails.getMember();
        return itemService.createItem(member, main_image, sub_images, requestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDto> updateItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @Valid @RequestParam(value = "main_image", required = false) MultipartFile new_mainImage,
            @Valid @RequestParam(value = "sub_image", required = false) List<MultipartFile> new_subImages,
            @RequestPart(value = "requestDto", required = false) itemRequestDto requestDto
            ) throws IOException {
        Member member = userDetails.getMember();
        return itemService.updateItem(member, id, new_mainImage, new_subImages, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        Member member = userDetails.getMember();
        return itemService.deleteItem(member, id);
    }

    // 거래 물품 단일 조회
    @GetMapping("/{item_id}")
    public ItemResponseDto showItem(
            @PathVariable Long item_id
    ) {
        return itemService.showItem(item_id);
    }


    @GetMapping
    public ResponseEntity<Page<ItemSearchResponseDto>> searchItem(
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {
        return itemService.searchItem(keyword, pageable);
    }

}
