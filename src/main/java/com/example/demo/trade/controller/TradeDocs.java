package com.example.demo.trade.controller;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.trade.dto.ItemStateRequestDto;
import com.example.demo.trade.dto.MyOrdersResponseDto;
import com.example.demo.trade.dto.MySalesResponseDto;
import com.example.demo.trade.dto.TradeRequestDto;
import com.example.demo.trade.type.State;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.file.AccessDeniedException;

@Tag(
        name = "Trade API",
        description = "거래 관련 API"
)
public interface TradeDocs {
    @Operation(
            summary = "구매 내역 조회 API",
            description = """
                    사용자가 구매한 내역.
                    """
    )
    ResponseEntity<Page<MyOrdersResponseDto>> readMyPageOrders(
            UserDetailsImpl principal,
            State[] stateList,
            Pageable pageable
    );

    @Operation(
            summary = "판매 내역 조회 API",
            description = """
                    사용자가 판매한 내역.
                    """
    )
    ResponseEntity<Page<MySalesResponseDto>> readMyPageSales(
            UserDetailsImpl principal,
            State[] stateList,
            Pageable pageable
    );

    @Operation(
            summary = "상품 상태 변경 API",
            description = """
                    상품 상태 변경 API
                    """
    )
    ResponseEntity<MessageResponseDto> updateItemState(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @RequestBody ItemStateRequestDto tradeRequestDto
    ) throws AccessDeniedException;

    @Operation(
            summary = "거래 기록 기입 API",
            description = """
                    거래 기록 기입 API
                    """
    )
    ResponseEntity<MessageResponseDto> updateTradeRecord(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @PathVariable Long itemId,
            @RequestBody TradeRequestDto tradeRequestDto
    ) throws AccessDeniedException;
}
