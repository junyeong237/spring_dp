package com.example.dp.domain.admin.service;

import com.example.dp.domain.category.dto.request.CategoryRequestDto;
import com.example.dp.domain.category.dto.response.CategoryResponseDto;

public interface AdminCategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto requestDto);

    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto requestDto);

    void deleteCategory(Long categoryId);
}
