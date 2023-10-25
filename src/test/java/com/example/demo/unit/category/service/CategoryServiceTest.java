package com.example.demo.unit.category.service;

import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.category.entity.CategoryL;
import com.example.demo.category.entity.CategoryM;
import com.example.demo.category.repository.CategoryLRepository;
import com.example.demo.category.repository.CategoryMRepository;
import com.example.demo.category.service.CategoryService;
import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {
    private final CategoryLRepository categoryLRepository = mock(CategoryLRepository.class);
    private final CategoryMRepository categoryMRepository = mock(CategoryMRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    private final CategoryService categoryService = new CategoryService(
            categoryLRepository, categoryMRepository, itemRepository
    );

    @Test
    @DisplayName("[정상 작동] readChildCategory 정상 작동 여부 확인.")
    void readChildCategory() {
        // given
        Long categoryId = 1L;

        CategoryL categoryLargeEntity = mock(CategoryL.class);
        CategoryM categoryMidEntity = mock(CategoryM.class);
        List<CategoryM> categoryMList = List.of(categoryMidEntity);

        when(categoryLRepository.findById(any()))
                .thenReturn(Optional.of(categoryLargeEntity));
        when(categoryLargeEntity.getCategoryMiddles())
                .thenReturn(categoryMList);

        // when
        ResponseEntity<List<CategoryResponseDto>> result = categoryService.readChildCategory(categoryId);

        // then
        assertFalse(result.getBody().isEmpty());

    }

    @Test
    @DisplayName("[정상 작동] readChildItem 대분류 카테고리 조회 작동 여부 확인.")
    void testReadChildItemWithLargeCategory() {
        // given
        Long categoryId = 1L;
        int layer = 1;

        Item itemEntity = mock(Item.class);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> itemPage = new PageImpl<>(List.of(itemEntity));

        when(itemRepository.findByCategoryLargeId(any(), any()))
                .thenReturn(itemPage);

        // when
        categoryService.readChildItem(categoryId, layer, pageable);

        // then
        verify(itemRepository, times(1))
                .findByCategoryLargeId(any(), any());

    }

    @Test
    @DisplayName("[정상 작동] readChildItem 중분류 카테고리 조회 작동 여부 확인.")
    void testReadChildItemWithMiddleCategory() {
        // given
        Long categoryId = 1L;
        int layer = 2;

        Item itemEntity = mock(Item.class);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> itemPage = new PageImpl<>(List.of(itemEntity));

        when(itemRepository.findByCategoryMiddleId(any(), any()))
                .thenReturn(itemPage);

        // when
        categoryService.readChildItem(categoryId, layer, pageable);

        // then
        verify(itemRepository, times(1))
                .findByCategoryMiddleId(any(), any());

    }

    @Test
    @DisplayName("[정상 작동] readCategory 대분류 카테고리 조회 작동 여부 확인.")
    void testReadCategoryWithLargeCategory() {
        // given
        Long categoryId = 1L;
        int layer = 1;

        Item itemEntity = mock(Item.class);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> itemPage = new PageImpl<>(List.of(itemEntity));

        when(itemRepository.findByCategoryLargeId(any(), any()))
                .thenReturn(itemPage);

        // when
        categoryService.readChildItem(categoryId, layer, pageable);

        // then
        verify(itemRepository, times(1))
                .findByCategoryLargeId(any(), any());

    }

    @Test
    @DisplayName("[정상 작동] readCategory 중분류 카테고리 조회 작동 여부 확인.")
    void testReadCategoryWithMiddleCategory() {
        // given
        Long categoryId = 1L;
        int layer = 2;

        Item itemEntity = mock(Item.class);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> itemPage = new PageImpl<>(List.of(itemEntity));

        when(itemRepository.findByCategoryMiddleId(any(), any()))
                .thenReturn(itemPage);

        // when
        categoryService.readChildItem(categoryId, layer, pageable);

        // then
        verify(itemRepository, times(1))
                .findByCategoryMiddleId(any(), any());

    }
}