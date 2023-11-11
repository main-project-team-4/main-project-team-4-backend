package com.example.demo.category.service;

import com.example.demo.category.dto.CategoryBundleResponseDto;
import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.category.dto.ItemInCategoryResponseDto;
import com.example.demo.category.entity.CategoryL;
import com.example.demo.category.entity.CategoryM;
import com.example.demo.category.repository.CategoryLRepository;
import com.example.demo.category.repository.CategoryMRepository;
import com.example.demo.category.type.CategoryType;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.trade.type.State;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryLRepository categoryLRepository;
    private final CategoryMRepository categoryMRepository;
    private final ItemRepository itemRepository;

    public ResponseEntity<List<CategoryResponseDto>> readChildCategory(
            Long categoryId
    ) {
        CategoryL entity = findCategoryLargeById(categoryId);
        List<CategoryResponseDto> dtoList = entity.getCategoryMiddles().stream()
                .map(CategoryResponseDto::new)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<Page<ItemInCategoryResponseDto>> readChildItem(
            Long categoryId, int layer,
            State[] state, Pageable pageable) {
        CategoryType categoryType = getTypeByLayer(layer);

        Page<ItemInCategoryResponseDto> response = null;
        if (categoryType == CategoryType.LARGE) {
            response = itemRepository.searchByCategoryLargeId(categoryId, state, pageable)
                    .map(ItemInCategoryResponseDto::new);

        } else if (categoryType == CategoryType.MIDDLE) {
            response = itemRepository.searchByCategoryMiddleId(categoryId, state, pageable)
                    .map(ItemInCategoryResponseDto::new);

        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<CategoryResponseDto> readCategory(
            Long categoryId, int layer
    ) {
        CategoryType categoryType = getTypeByLayer(layer);

        CategoryResponseDto response = null;
        if (categoryType == CategoryType.LARGE) {
            CategoryL entity = findCategoryLargeById(categoryId);
            response = new CategoryResponseDto(entity);

        } else if (categoryType == CategoryType.MIDDLE) {
            CategoryM entity = findCategoryMiddleById(categoryId);
            response = new CategoryResponseDto(entity);

        }

        return ResponseEntity.ok(response);
    }

    private CategoryL findCategoryLargeById(Long Id) {
        return categoryLRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("해당 대분류 카테고리는 존재하지 않습니다."));
    }

    private CategoryM findCategoryMiddleById(Long Id) {
        return categoryMRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("해당 중분류 카테고리는 존재하지 않습니다."));
    }

    private CategoryType getTypeByLayer(int layer) {
        return CategoryType.getTypeByLayer(layer)
                .orElseThrow(() -> new IllegalArgumentException(layer + " 라는 Layer는 존재하지 않습니다."));
    }

    public ResponseEntity<List<CategoryResponseDto>> readItemsLarge() {
        List<CategoryResponseDto> categoryList = categoryLRepository.findAll().stream()
                .map(CategoryResponseDto::new)
                .toList();
        return ResponseEntity.ok(categoryList);
    }

    public ResponseEntity<List<CategoryBundleResponseDto>> readCategoryRecursively() {
        List<CategoryBundleResponseDto> dtoList = categoryLRepository.findAllRecursively().stream()
                .map(CategoryBundleResponseDto::new)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}
