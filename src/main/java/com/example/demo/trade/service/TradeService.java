package com.example.demo.trade.service;

import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final ItemRepository itemRepository;

    public ResponseEntity<Page<ItemSearchResponseDto>> readOrders(Member member, Pageable pageable) {
        Page<ItemSearchResponseDto> dtoPage = itemRepository.findPurchaseItemByMember_id(member.getId(), pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoPage);
    }

    public ResponseEntity<Page<ItemSearchResponseDto>> readSales(Member member, Pageable pageable) {
        Page<ItemSearchResponseDto> dtoPage = itemRepository.findSellingItemByMember_id(member.getId(), pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoPage);
    }
}
