package com.example.demo.shop.controller;


import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.member.dto.ShopPageMemberResponseDto;
import com.example.demo.member.dto.SignupRequestDto;
import com.example.demo.member.entity.Member;
import com.example.demo.shop.dto.ShopRequestDto;
import com.example.demo.shop.dto.ShopResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Shop API",
        description = "상점 API"
)
public interface ShopDocs {

    @Operation(
            summary = "상점 생성 API",
            description = """
                    상점 생성 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ShopResponseDto.class)
            )//응답받을 데이터 타입
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 상점은 존재하지 않음.",
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

    ResponseEntity<MessageResponseDto> createShop(
            @Parameter(
                    description = "상점 생성 시, 필요한 정보들.",
                    schema = @Schema(implementation = ShopRequestDto.class)//요청할 데이터 타입
            )
            @RequestBody SignupRequestDto requestDto, Member member);

    @Operation(
            summary = "상점 수정 API",
            description = """
                    상점 수정 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ShopResponseDto.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 상점은 존재하지 않음.",
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
    ResponseEntity<MessageResponseDto> updateShop(
            @Parameter(
                    description = "상점 생성 시, 필요한 정보들.",
                    schema = @Schema(implementation = ShopRequestDto.class)//요청할 데이터 타입
            )
            Long shopId, ShopRequestDto requestDto);

    @Operation(
            summary = "상점 삭제 API",
            description = """
                    상점 삭제 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.TEXT_PLAIN_VALUE
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 상점은 존재하지 않음.",
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
    ResponseEntity<MessageResponseDto> deleteShop(@PathVariable("shopId") Long shopId);

    @Operation(
            summary = "상점 페이지 판매 상품 목록 조회 API",
            description = """
                    상점 페이지 판매 상품 목록 조회 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ItemSearchResponseDto.class)
            )
    )
    ResponseEntity<Page<ItemSearchResponseDto>> readItemsOfShop(
            Long shopId,
            Pageable pageable
    );

    @Operation(
            summary = "상점 페이지 회원 정보 조회 API",
            description = """
                    상점 페이지 회원 정보 조회 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ShopPageMemberResponseDto.class)
            )
    )
    ResponseEntity<ShopPageMemberResponseDto> readShopPage(
            @PathVariable Long shopId
    );
}
