package com.example.dp.domain.menu.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.dp.domain.menu.dto.response.MenuSimpleResponseDto;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.menu.service.MenuService;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import java.util.List;
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
    MenuService menuService;

    FixtureMonkey fixtureMonkey;

    final int SAMPLE_COUNT = 5;

    @BeforeEach
    void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();
        List<Menu> menus = fixtureMonkey.giveMeBuilder(Menu.class)
            .setNotNull("*")
            .setNull("menuCategoryList")
            .sampleList(SAMPLE_COUNT);
        menuRepository.saveAll(menus);
    }

    @Transactional
    @Test
    @DisplayName("전체 메뉴를 조회한다.")
    void getAllMenus() {
        // given

        // when
        List<MenuSimpleResponseDto> responseDto = menuService.getAllMenus();

        // then
        assertThat(responseDto).hasSize(SAMPLE_COUNT);
    }
}