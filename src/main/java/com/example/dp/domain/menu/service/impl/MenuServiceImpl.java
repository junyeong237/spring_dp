package com.example.dp.domain.menu.service.impl;

import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.category.exception.CategoryErrorCode;
import com.example.dp.domain.category.exception.NotFoundCategoryException;
import com.example.dp.domain.category.repository.CategoryRepository;
import com.example.dp.domain.menu.dto.response.MenuSimpleResponseDto;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.menu.service.MenuService;
import com.example.dp.domain.menucategory.entity.MenuCategory;
import com.example.dp.domain.menucategory.repository.MenuCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MenuSimpleResponseDto> getMenus(final String categoryType, final String menuName) {
        List<MenuSimpleResponseDto> responseDto;
        if (!categoryType.isEmpty()) {
            responseDto = getMenusWithCategory(categoryType);
        } else if (!menuName.isEmpty()) {
            responseDto = getMenuWithName(menuName);
        } else {
            responseDto = getAllMenus();
        }
        return responseDto;
    }

    private List<MenuSimpleResponseDto> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();

        return menus.stream()
            .map(MenuSimpleResponseDto::new)
            .toList();
    }

    private List<MenuSimpleResponseDto> getMenusWithCategory(final String categoryType) {
        Category category = categoryRepository.findByType(categoryType)
            .orElseThrow(() -> new NotFoundCategoryException(
                CategoryErrorCode.NOT_FOUND_CATEGORY));

        List<MenuCategory> menuCategories = menuCategoryRepository
            .findByCategoryId(category.getId());

        return menuCategories.stream()
            .map(menuCategory -> new MenuSimpleResponseDto(menuCategory.getMenu()))
            .toList();
    }

    private List<MenuSimpleResponseDto> getMenuWithName(final String menuName) {
        List<Menu> menu = menuRepository.findByNameContains(menuName);

        return menu.stream()
            .map(MenuSimpleResponseDto::new)
            .toList();
    }
}
