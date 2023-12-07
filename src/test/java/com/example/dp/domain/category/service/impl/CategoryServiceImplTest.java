package com.example.dp.domain.category.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.dp.domain.category.dto.request.CategoryRequestDto;
import com.example.dp.domain.category.dto.response.CategoryResponseDto;
import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.category.exception.ExistsCategoryTypeException;
import com.example.dp.domain.category.exception.NotFoundCategoryException;
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

        @DisplayName("카테고리 생성 실패 : 중복된 카테고리")
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
                .isInstanceOf(ExistsCategoryTypeException.class);
        }
    }

    @DisplayName("카테고리 수정 테스트")
    @Nested
    class updateCategoryTest {

        @DisplayName("카테고리 수정 성공")
        @Test
        void 카테고리_수정_성공() {
            // Given
            Category category = fixtureMonkey.giveMeBuilder(Category.class)
                .setNotNull("*")
                .sample();

            CategoryRequestDto requestDto = new CategoryRequestDto(category.getType());
            CategoryResponseDto responseDto1 = categoryService.createCategory(requestDto);
            Category findCategory = categoryRepository.findById(responseDto1.getId())
                .orElseThrow(RuntimeException::new);

            // When
            CategoryResponseDto responseDto2 = categoryService.updateCategory(
                responseDto1.getId(), new CategoryRequestDto("수정 테스트"));

            // Then
            assertThat(responseDto2.getId()).isEqualTo(responseDto1.getId());
            assertThat(responseDto2.getType()).isEqualTo("수정 테스트");
        }

        @DisplayName("카테고리 수정 실패 : 없는 카테고리")
        @Test
        void 카테고리_수정_실패() {
            // Given x

            // When - Then
            assertThatThrownBy(() -> categoryService.updateCategory(
                2214125125151L, new CategoryRequestDto("수정 테스트")))
                .isInstanceOf(NotFoundCategoryException.class);
        }
    }

    @DisplayName("카테고리 삭제 테스트")
    @Nested
    class deleteCategory{
        @DisplayName("카테고리 삭제 성공")
        @Test
        void 카테고리_삭제_성공(){
            // Given
            Category category = fixtureMonkey.giveMeBuilder(Category.class)
                .setNotNull("*")
                .sample();

            CategoryRequestDto requestDto = new CategoryRequestDto(category.getType());
            CategoryResponseDto responseDto = categoryService.createCategory(requestDto);

            // When
            categoryService.deleteCategory(responseDto.getId());

            // Then
            assertThat(categoryRepository.existsById(responseDto.getId())).isEqualTo(false);
        }

        @DisplayName("카테고리 삭제 실패 : 없는 카테고리")
        @Test
        void 카테고리_삭제_실패() {
            // Given x

            // When - Then
            assertThatThrownBy(() -> categoryService.deleteCategory(1023241251L))
                .isInstanceOf(NotFoundCategoryException.class);
        }
    }
}