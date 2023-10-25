package com.example.demo.category.controller;


import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.category.dto.ItemInCategoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(
        name = "카테고리 API",
        description = "카테고리 API")
public interface CategoryDocs {

    @Operation(
            summary = "카테고리 단일 조회",
            description = """
                    카테고리 단일 조회
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CategoryResponseDto.class)
            )//응답받을 데이터 타입
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 카테고리는 존재하지 않음.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
            ))
    @ApiResponse( //응답받을 데이터 타입
            responseCode = "500",
            description = "서버 에러",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
            )
    )
    ResponseEntity<CategoryResponseDto> read(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "2") int layer
    );

    @Operation(
            summary = "특정 대분류 아래의 중분류 카테고리 목록 조회",
            description = """
                    특정 대분류 아래의 중분류 카테고리 목록 조회
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CategoryResponseDto.class)
            )//응답받을 데이터 타입
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 카테고리는 존재하지 않음.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
            ))
    @ApiResponse( //응답받을 데이터 타입
            responseCode = "500",
            description = "서버 에러",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
            )
    )
    ResponseEntity<List<CategoryResponseDto>> readChildCategory(
            @PathVariable Long categoryId
    );

    @Operation(
            summary = "특정 대분류 아래의 카테고리 목록 조회",
            description = """
                    특정 대분류 아래의 카테고리 목록 조회
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ItemInCategoryResponseDto.class)
            )//응답받을 데이터 타입
    )
    @ApiResponse(
            responseCode = "404",
            description = "'~~'번 카테고리는 존재하지 않음.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
            ))
    @ApiResponse( //응답받을 데이터 타입 //응답받을 데이터 타입
            responseCode = "500",
            description = "서버 에러",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
            )
    )
    ResponseEntity<Page<ItemInCategoryResponseDto>> readChildItem(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "2") int layer,
            Pageable pageable
    );

    @Operation(
            summary = "모든 대분류 카테고리 목록 조회",
            description = """
                    모든 대분류 카테고리 목록 조회
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CategoryResponseDto.class)
            )//응답받을 데이터 타입
    )
    ResponseEntity<List<CategoryResponseDto>> readCategoryLarge();

}
