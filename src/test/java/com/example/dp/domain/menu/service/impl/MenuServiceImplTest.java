package com.example.dp.domain.menu.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.category.repository.CategoryRepository;
import com.example.dp.domain.menu.dto.response.MenuSimpleResponseDto;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.menu.service.MenuService;
import com.example.dp.domain.menucategory.entity.MenuCategory;
import com.example.dp.domain.menucategory.repository.MenuCategoryRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MenuServiceImplTest {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MenuCategoryRepository menuCategoryRepository;
    @Autowired
    MenuService menuService;

    FixtureMonkey fixtureMonkey;

    final int SAMPLE_COUNT = 10;

    @BeforeEach
    void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();
        List<Menu> menus = fixtureMonkey.giveMeBuilder(Menu.class)
            .setNotNull("*")
            .setNull("id")
            .setNull("menuCategoryList")
            .sampleList(SAMPLE_COUNT);
        menuRepository.saveAll(menus);

        Category mainCategory = Category.builder()
            .type("메인 메뉴")
            .build();
        categoryRepository.save(mainCategory);
        Category dessertCategory = Category.builder()
            .type("디저트 메뉴")
            .build();
        categoryRepository.save(dessertCategory);

        for (int i = 0; i < menus.size() / 2; i++) {
            MenuCategory menuCategory = MenuCategory.builder()
                .menu(menus.get(i))
                .category(mainCategory)
                .build();
            menuCategoryRepository.save(menuCategory);
        }
    }

    @AfterEach
    void post(){
        menuCategoryRepository.deleteAll();
        menuRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("전체 메뉴를 조회한다.")
    void getAllMenus() {
        // given

        // when
        List<MenuSimpleResponseDto> responseDto = menuService.getMenus("", "");

        // then
        assertThat(responseDto).hasSize(SAMPLE_COUNT);
    }

    @Test
    @DisplayName("이름으로 메뉴를 조회한다.")
    void getMenuWithName() {
        // given
        Menu menu = menuRepository.findAll().get(0);

        // when
        List<MenuSimpleResponseDto> responseDto = menuService.getMenus("", menu.getName());

        // then
        assertThat(responseDto.get(0)).extracting("name").isEqualTo(menu.getName());
    }

    @Test
    @DisplayName("카테고리 기준으로 메뉴를 조회한다.")
    void getMenusWithCategory() {
        // given

        // when
        List<MenuSimpleResponseDto> responseDto = menuService.getMenus("메인 메뉴", "");

        // then
        assertThat(responseDto).hasSize(SAMPLE_COUNT / 2);
    }
}