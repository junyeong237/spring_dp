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
import com.example.dp.domain.menulike.entity.MenuLike;
import com.example.dp.domain.menulike.repository.MenuLikeRepository;
import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.repository.UserRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Disabled
class MenuServiceImplTest {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MenuCategoryRepository menuCategoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MenuLikeRepository menuLikeRepository;
    @Autowired
    MenuService menuService;

    final int SAMPLE_COUNT = 10;

    @BeforeEach
    void setUp() {
        List<Menu> menus = new ArrayList<>();
        for(int i=0;i<SAMPLE_COUNT;i++){
            menus.add(
                Menu.builder()
                    .name("asdsa"+i)
                    .price(i*100)
                    .description("test")
                    .status(true)
                    .quantity(i+5)
                    .build()
            );
        }
        menus = menuRepository.saveAll(menus);

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

        User user = User.builder()
            .role(UserRole.USER)
            .username("test")
            .email("test@test.test")
            .password("test")
            .build();
        user = userRepository.save(user);

        MenuLike menuLike = MenuLike.builder()
            .menu(menus.get(0))
            .user(user)
            .build();
        menuLikeRepository.save(menuLike);
    }

    @AfterEach
    void post(){
        menuLikeRepository.deleteAll();
        menuCategoryRepository.deleteAll();
        menuRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
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

    @Test
    @DisplayName("사용자의 좋아요 기준으로 메뉴를 조회한다.")
    void getMenusWithLike() {
        // given
        User user = userRepository.findAll().get(0);

        // when
        List<MenuSimpleResponseDto> responseDto = menuService.getLikeMenus(user);

        // then
        assertThat(responseDto).hasSize(1);
    }
}