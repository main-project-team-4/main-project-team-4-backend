package com.example.demo.trade.controller;

import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

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
    ResponseEntity<Page<ItemSearchResponseDto>> readMyPageOrders(
            UserDetailsImpl principal,
            Pageable pageable
    );

    @Operation(
            summary = "판매 내역 조회 API",
            description = """
                    사용자가 판매한 내역.
                    """
    )
    ResponseEntity<Page<ItemSearchResponseDto>> readMyPageSales(
            UserDetailsImpl principal,
            Pageable pageable
    );
}
