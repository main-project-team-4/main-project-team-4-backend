package com.example.demo.trade.controller;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.trade.dto.ItemStateRequestDto;
import com.example.demo.trade.dto.MyOrdersResponseDto;
import com.example.demo.trade.dto.MySalesResponseDto;
import com.example.demo.trade.dto.TradeRequestDto;
import com.example.demo.trade.service.TradeService;
import com.example.demo.trade.type.State;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TradeController implements TradeDocs {
    private final TradeService tradeService;

    @GetMapping("/api/mypages/orders")
    public ResponseEntity<Page<MyOrdersResponseDto>> readMyPageOrders(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @RequestParam(defaultValue = "SOLDOUT") State[] stateList,
            @PageableDefault Pageable pageable
            ) {
        return tradeService.readOrders(principal.getMember(), stateList, pageable);
    }

    @GetMapping("/api/mypages/sales")
    public ResponseEntity<Page<MySalesResponseDto>> readMyPageSales(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @RequestParam(defaultValue = "SOLDOUT") State[] stateList,
            @PageableDefault Pageable pageable
    ) {
        return tradeService.readSales(principal.getMember(), stateList, pageable);
    }

    @PostMapping("/api/trades")
    public ResponseEntity<MessageResponseDto> updateItemState(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @RequestBody ItemStateRequestDto requestDto
    ) {
        return tradeService.updateItemState(principal.getMember(), requestDto);
    }

    @PostMapping("/api/items/{itemId}/trades")
    public ResponseEntity<MessageResponseDto> updateTradeRecord(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @PathVariable Long itemId,
            @RequestBody TradeRequestDto tradeRequestDto
    ) {
        return tradeService.updateTradeRecord(principal.getMember(), tradeRequestDto, itemId);
    }
}
