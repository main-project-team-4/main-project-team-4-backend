package com.example.demo.review.controller;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.review.dto.ReviewRequestDto;
import com.example.demo.review.dto.ReviewResponseDto;
import com.example.demo.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Review API",
        description = "리뷰 API"
)
public interface ReviewDocs {
    @Operation(
            summary = "댓글 생성 API",
            description = """
                    댓글 생성 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ReviewResponseDto.class)
            )//응답받을 데이터 타입
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 리뷰는 존재하지 않음.",
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
    ResponseEntity<MessageResponseDto> createReview(
            @Parameter(
                    description = "댓글 생성 시, 필요한 정보들.",
                    schema = @Schema(implementation = ReviewRequestDto.class)//요청할 데이터 타입
            )
            @RequestBody @Valid ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails);
    /////////

    @Operation(
            summary = "상품 리뷰 열람 API",
            description = """
                    상품 리뷰 열람 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ReviewResponseDto.class)
            )
    )
    ResponseEntity<ReviewResponseDto> readReviewOfItem(
            Long itemId
    );


    @Operation(
            summary = "댓글 수정 API",
            description = """
                    댓글 수정 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ReviewResponseDto.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 리뷰는 존재하지 않음.",
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
    ResponseEntity<MessageResponseDto> updateReview(
            @Parameter(
                    description = "댓글 수정 시, 필요한 정보들.",
                    schema = @Schema(implementation = ReviewRequestDto.class)//요청할 데이터 타입
            )
            @PathVariable Long review_id, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails);


    @Operation(
            summary = "댓글 삭제 API",
            description = """
                    댓글 삭제 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.TEXT_PLAIN_VALUE
            )
    )
    ResponseEntity<MessageResponseDto> deleteReview(@PathVariable Long review_id, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(
            summary = "상점 리뷰 목록 API",
            description = """
                    상점 리뷰 목록 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ReviewResponseDto.class)
            )
    )
    ResponseEntity<Page<ReviewResponseDto>> readReviewList(
            Long shopId,
            Pageable pageable
    );
}
