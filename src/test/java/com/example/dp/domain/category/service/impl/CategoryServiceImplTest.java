package com.example.dp.domain.category.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.dp.domain.category.dto.request.CategoryRequestDto;
import com.example.dp.domain.category.dto.response.CategoryResponseDto;
import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.category.repository.CategoryRepository;
import com.example.dp.domain.category.service.CategoryService;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setup() {
        fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();
    }

    @DisplayName("카테고리 생성 테스트")
    @Nested
    class CreateCategoryTest {

        @DisplayName("카테고리 생성 성공")
        @Test
        void 카테고리_생성_성공() {
            // Given
            CategoryRequestDto requestDto = new CategoryRequestDto("테스트 타입");

            // When
            CategoryResponseDto responseDto = categoryService.createCategory(requestDto);

            // Then
            Category findCategory = categoryRepository.findByType(responseDto.getType())
                .orElseThrow(RuntimeException::new);
            assertThat(findCategory.getId()).isEqualTo(responseDto.getId());
            assertThat(findCategory.getType()).isEqualTo(responseDto.getType());
        }

        @DisplayName("중복된 카테고리 생성 실패")
        @Test
        void 중복된_카테고리_생성_실패() {
            // Given
            Category category = fixtureMonkey.giveMeBuilder(Category.class)
                .setNotNull("*")
                .sample();

            CategoryRequestDto requestDto = new CategoryRequestDto(category.getType());
            categoryService.createCategory(requestDto);

            // When - Then
            assertThatThrownBy(() -> categoryService.createCategory(requestDto))
                .isInstanceOf(DataIntegrityViolationException.class);
        }
    }
}