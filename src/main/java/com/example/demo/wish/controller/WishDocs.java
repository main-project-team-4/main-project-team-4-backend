package com.example.demo.wish.controller;


import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.trade.type.State;
import com.example.demo.wish.dto.TopItemResponseDto;
import com.example.demo.wish.dto.WishListResponseDto;
import com.example.demo.wish.dto.WishReadResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(
        name = "찜 API",
        description = "찜 API"
)
public interface WishDocs {
    @Operation(
            summary = "찜 API",
            description = """
                    찜 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동"
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 찜은 존재하지 않음.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "서버 에러",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    ResponseEntity<Void> toggleWish(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @PathVariable Long itemId
    );

    @Operation(
            summary = "찜 여부 확인 API",
            description = """
                    찜 여부 확인  API. <br>
                    JWT 토큰 필수.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = WishReadResponseDto.class)
            )
    )
    ResponseEntity<WishReadResponseDto> readWishRecord(
            UserDetailsImpl principal,
            Long itemId
    );

    @Operation(
            summary = "마이페이지 찜한 상품 목록 조회 API",
            description = """
                    마이페이지 찜한 상품 목록 조회 API.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = WishListResponseDto.class)
            )
    )
    ResponseEntity<List<WishListResponseDto>> readMyWishLists(
            UserDetailsImpl principal
    );


    @Operation(
            summary = "인기순 목록 조회 API",
            description = """
                    인기순 목록 조회 API.<br>
                    만약 특정 판매 완료 상태를 제외하고 필터링을 하고 싶다면 아래와 같이 호출하시면 됩니다.<br>
                    /api/top-items?state=SELLING&state=RESERVED
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TopItemResponseDto.class)
            )
    )
    ResponseEntity<Page<TopItemResponseDto>> readPopularItems(
            State[] stateList,
            Pageable pageable
    );
}
