package com.example.demo.location.controller;


import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.location.dto.LocationRequestDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.trade.type.State;
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
        name = "Location API",
        description = "Location API"
)
public interface LocationDocs {
    @Operation(
            summary = "Location 생성 API",
            description = """
                    Location 생성 API
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동"
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 Location은 존재하지 않음.",
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
    ResponseEntity<MessageResponseDto> createLocation(
            @Parameter(
                    description = "Location 생성 시, 필요한 정보들.",
                    schema = @Schema(implementation = LocationRequestDto.class)//요청할 데이터 타입
            )
            @RequestBody @Valid LocationRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(
            summary = "Location 삭제 API",
            description = """
                    Location 삭제 API
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
            description = "'~~'번 Location은 존재하지 않음.",
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
    public ResponseEntity<MessageResponseDto> deleteLocation(@PathVariable Long location_id, @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(
            summary = "근처 상품 API",
            description = """
                    근처 상품 API
                    """
    )
    ResponseEntity<Page<ItemSearchResponseDto>> readNearbyItems(
            UserDetailsImpl principal,
            State[] state,
            Pageable pageable
    );
}
