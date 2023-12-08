package com.example.dp.domain.admin.controller;

import com.example.dp.domain.category.dto.request.CategoryRequestDto;
import com.example.dp.domain.category.dto.response.CategoryResponseDto;
import com.example.dp.domain.admin.service.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseDto> createCategory(
        @RequestBody CategoryRequestDto requestDto) {
        CategoryResponseDto responseDto = adminCategoryService.createCategory(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
        @PathVariable Long categoryId,
        @RequestBody CategoryRequestDto requestDto) {
        CategoryResponseDto responseDto = adminCategoryService.updateCategory(categoryId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
        @PathVariable Long categoryId){
        adminCategoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
