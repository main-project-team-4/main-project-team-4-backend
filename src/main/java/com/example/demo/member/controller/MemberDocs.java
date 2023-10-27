package com.example.demo.member.controller;


import com.example.demo.dto.MessageResponseDto;
import com.example.demo.member.dto.LocationRequestDto;
import com.example.demo.member.dto.MemberInfoRequestDto;
import com.example.demo.member.dto.MyPageMemberResponseDto;
import com.example.demo.member.dto.ShopPageMemberResponseDto;
import com.example.demo.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "회원 API",
        description = "회원 API"
)
public interface MemberDocs {
    @Operation(
            summary = "회원 수정 API",
            description = """
                    회원 수정 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동"
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 회원은 존재하지 않음.",
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
    ResponseEntity<MessageResponseDto> updateMember(
            MemberInfoRequestDto request,
            UserDetailsImpl principal);


    @Operation(
            summary = "회원 탈퇴 API",
            description = """
                    회원 탈퇴 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 회원은 존재하지 않음.",
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
    ResponseEntity<MessageResponseDto> deleteMember(UserDetailsImpl principal);


    @Operation(
            summary = "회원 위치 정보 수정 API",
            description = """
                    회원 위치 정보 수정 API.<br>
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동"
    )
    ResponseEntity<MessageResponseDto> updateMemberLocations(
            LocationRequestDto request,
            UserDetailsImpl principal
    );

    @Operation(
            summary = "회원 프로필 사진 정보 수정 API",
            description = """
                    회원 프로필 사진 정보 수정 API.<br>
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
    ResponseEntity<MessageResponseDto> updateProfileImage(
            @Parameter(
                    description = "multipart/form-data 형식의 이미지 1개를 input으로 받습니다.",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            MultipartFile image,
            UserDetailsImpl principal
    );

    @Operation(
            summary = "회원 정보 조회 API",
            description = """
                    회원 정보 조회 API.<br>
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MyPageMemberResponseDto.class)
            )
    )
    ResponseEntity<MyPageMemberResponseDto> readMyPageMember(
            UserDetailsImpl principal
    );

    @Operation(
            summary = "상점 페이지 내의 회원 정보 조회 API",
            description = """
                    상점 페이지 내의 회원 정보 조회 API.<br>
                    제 3자가 볼 수 있는 정보만 담깁니다.
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
    ResponseEntity<ShopPageMemberResponseDto> readMemberInShopPage(
            Long memberId
    );
}
