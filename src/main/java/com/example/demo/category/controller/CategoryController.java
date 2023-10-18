package com.example.demo.category.controller;

import com.example.demo.category.dto.CategoryBundleResponseDto;
import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.category.dto.ItemInCategoryResponseDto;
import com.example.demo.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryDocs{
    private final CategoryService categoryService;

    @GetMapping("/api/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> read(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "2") int layer
    ) {
        return categoryService.readCategory(categoryId, layer);
    }

    @GetMapping("/api/categories/{categoryId}/categories")
    public ResponseEntity<List<CategoryResponseDto>> readChildCategory(
            @PathVariable Long categoryId
    ) {
        return categoryService.readChildCategory(categoryId);
    }

    @GetMapping("/api/categories/{categoryId}/items")
    public ResponseEntity<List<ItemInCategoryResponseDto>> readChildItem(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "2") int layer
    ) {
        return categoryService.readChildItem(categoryId, layer);
    }

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponseDto>> readCategoryLarge() {
        return categoryService.readItemsLarge();
    }

    @GetMapping("/api/categories/all/categories")
    public ResponseEntity<List<CategoryBundleResponseDto>> readCategoryRecursively() {
        return categoryService.readCategoryRecursively();
    }
}
