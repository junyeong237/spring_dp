package com.example.dp.domain.category.dto.response;

import com.example.dp.domain.category.entity.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private final Long id;
    private final String type;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.type = category.getType();
    }
}
