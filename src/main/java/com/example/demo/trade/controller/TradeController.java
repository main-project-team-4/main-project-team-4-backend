package com.example.demo.trade.controller;

import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradeController implements TradeDocs {
    private final TradeService tradeService;

    @GetMapping("/api/mypages/orders")
    public ResponseEntity<Page<ItemSearchResponseDto>> readMyPageOrders(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @PageableDefault Pageable pageable
            ) {
        return tradeService.readOrders(principal.getMember(), pageable);
    }

    @GetMapping("/api/mypages/sales")
    public ResponseEntity<Page<ItemSearchResponseDto>> readMyPageSales(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @PageableDefault Pageable pageable
    ) {
        return tradeService.readSales(principal.getMember(), pageable);
    }
}
