package com.example.demo.follow.controller;


import com.example.demo.dto.MessageResponseDto;
import com.example.demo.follow.dto.FollowMemberResponseDto;
import com.example.demo.follow.dto.FollowResponseDto;
import com.example.demo.follow.dto.FollowersResponseDto;
import com.example.demo.security.UserDetailsImpl;
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

import java.util.List;

@Tag(
        name = "팔로우 API",
        description = "팔로우 API"
)
public interface FollowDocs {

    @Operation(
            summary = "팔로우 하기",
            description = """
                    팔로우 하기
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FollowMemberResponseDto.class)
            )
    )
     ResponseEntity<FollowResponseDto> toggleShopFollow(
             Long shopId,
             UserDetailsImpl principal
    );

    @Operation(
            summary = "팔로워 목록 조회",
            description = """
                    팔로워 목록 조회.<br>
                    만약 JWT를 추가로 보낸다면, 열람 대상이 각 팔로워를 팔로우하고 있는지 여부도 포함해서 알려줍니다.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FollowersResponseDto.class)
            )//응답받을 데이터 타입
    )
    ResponseEntity<List<FollowersResponseDto>> readFollowerListByShopId(
            @PathVariable Long memberId,
            UserDetailsImpl principal
    );

    @Operation(
            summary = "팔로잉 목록 조회",
            description = """
                    팔로잉 목록 조회
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FollowMemberResponseDto.class)
            )//응답받을 데이터 타입
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 팔로잉는 존재하지 않음.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
            ))
    @ApiResponse(
            responseCode = "500",
            description = "서버 에러",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    ResponseEntity<List<FollowMemberResponseDto>> readFollowingsByMemberId(
            @PathVariable Long memberId
    );

    @Operation(
            summary = "내 팔로워 목록 조회",
            description = """
                    내 팔로워 목록 조회
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FollowMemberResponseDto.class)
            )//응답받을 데이터 타입
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 팔로워는 존재하지 않음.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
            ))
    @ApiResponse(
            responseCode = "500",
            description = "서버 에러",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    ResponseEntity<List<FollowMemberResponseDto>> readFollowerListInMyPage(
            @AuthenticationPrincipal UserDetailsImpl principal
    );

    @Operation(
            summary = "팔로우 여부 조회 API",
            description = """
                    팔로우 여부 조회 API. <br>
                    로그인 유저가 해당 유저를 팔로우 했는지 확인.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FollowResponseDto.class)
            )
    )
    ResponseEntity<FollowResponseDto> readFollowersByMemberId(
            Long memberId,
            UserDetailsImpl principal
    );

    @Operation(
            summary = "팔로워 삭제 API",
            description = """
                    팔로워 삭제 API.<br>
                    팔로우 당한 사람이 아닌 사람의 접근은 403 에러를 발생시킵니다.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MessageResponseDto.class)
            )
    )
    ResponseEntity<MessageResponseDto> deleteFollow(
            Long followId,
            UserDetailsImpl principal
    );
}
