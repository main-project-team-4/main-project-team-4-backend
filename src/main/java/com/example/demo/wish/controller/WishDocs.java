package com.example.demo.wish.controller;


import com.example.demo.security.UserDetailsImpl;
import com.example.demo.wish.dto.WishReadResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;

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
}
