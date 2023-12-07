package com.example.dp.domain.category.service.impl;

import com.example.dp.domain.category.dto.request.CategoryRequestDto;
import com.example.dp.domain.category.dto.response.CategoryResponseDto;
import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.category.repository.CategoryRepository;
import com.example.dp.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDto createCategory(final CategoryRequestDto requestDto) {

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
        category.update(requestDto);
        return new CategoryResponseDto(category);
    }

    private Category findCategory(final Long categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(RuntimeException::new);
    }
}
