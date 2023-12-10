package com.example.dp.domain.admin.service.impl;

import com.example.dp.domain.category.dto.request.CategoryRequestDto;
import com.example.dp.domain.category.dto.response.CategoryResponseDto;
import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.category.exception.CategoryErrorCode;
import com.example.dp.domain.category.exception.ExistsCategoryTypeException;
import com.example.dp.domain.category.exception.ForbiddenDeleteCategoryException;
import com.example.dp.domain.category.exception.NotFoundCategoryException;
import com.example.dp.domain.category.repository.CategoryRepository;
import com.example.dp.domain.admin.service.AdminCategoryService;
import com.example.dp.domain.menucategory.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    @Override
    public CategoryResponseDto createCategory(final CategoryRequestDto requestDto) {

        if (categoryRepository.existsByType(requestDto.getType())) {
            throw new ExistsCategoryTypeException(CategoryErrorCode.EXISTS_CATEGORY_TYPE);
        }
        Category category = Category.builder()
            .type(requestDto.getType())
            .build();

        category = categoryRepository.save(category);

        return new CategoryResponseDto(category);
    }

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(
        final Long categoryId,
        final CategoryRequestDto requestDto) {
        Category category = findCategory(categoryId);
        category.update(requestDto.getType());
        return new CategoryResponseDto(category);
    }

    @Override
    public void deleteCategory(final Long categoryId) {
        Category category = findCategory(categoryId);

        if (menuCategoryRepository.existsByCategoryId(categoryId)) {
            throw new ForbiddenDeleteCategoryException(CategoryErrorCode.FORBIDEN_DELETE);
        }

        categoryRepository.delete(category);
    }

    private Category findCategory(final Long categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new NotFoundCategoryException(CategoryErrorCode.NOT_FOUND_CATEGORY));
    }
}
