package com.example.dp.domain.menu.service.impl;

import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.category.exception.CategoryErrorCode;
import com.example.dp.domain.category.exception.NotFoundCategoryException;
import com.example.dp.domain.category.repository.CategoryRepository;
import com.example.dp.domain.menu.dto.response.MenuSimpleResponseDto;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.exception.InvalidInputException;
import com.example.dp.domain.menu.exception.MenuErrorCode;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.menu.service.MenuService;
import com.example.dp.domain.menucategory.entity.MenuCategory;
import com.example.dp.domain.menucategory.repository.MenuCategoryRepository;
import com.example.dp.domain.review.entity.Review;
import java.util.Collections;
import com.example.dp.domain.menulike.entity.MenuLike;
import com.example.dp.domain.menulike.repository.MenuLikeRepository;
import com.example.dp.domain.user.entity.User;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final MenuLikeRepository menuLikeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MenuSimpleResponseDto> getMenus(final String categoryType, final String menuName,
        String sort) {
        List<MenuSimpleResponseDto> responseDto;
        if (!categoryType.isEmpty()) {
            responseDto = getMenusWithCategory(categoryType, sort);
        } else if (!menuName.isEmpty()) {
            responseDto = getMenuWithName(menuName, sort);
        } else {
            responseDto = getAllMenus(sort);
        }
        return responseDto;
    }

    private List<MenuSimpleResponseDto> getAllMenus(String sort) {

        List<Menu> menus = menuRepository.findAll();

        return filtering(menus,sort)
            .map(MenuSimpleResponseDto::new)
            .toList();


    }

    private List<MenuSimpleResponseDto> getMenusWithCategory(final String categoryType,
        String sort) {
        Category category = categoryRepository.findByType(categoryType)
            .orElseThrow(() -> new NotFoundCategoryException(
                CategoryErrorCode.NOT_FOUND_CATEGORY));

        List<MenuCategory> menuCategories = menuCategoryRepository
            .findByCategoryId(category.getId());

        List<Menu> menus = menuCategories.stream()
            .map(MenuCategory::getMenu)
            .toList();

        return filtering(menus, sort)
            .map(MenuSimpleResponseDto::new)
            .toList();
    }

    private List<MenuSimpleResponseDto> getMenuWithName(final String menuName, String sort) {
        List<Menu> menus = menuRepository.findByNameContains(menuName);

        return filtering(menus, sort)
            .map(MenuSimpleResponseDto::new)
            .toList();
    }

    private Stream<Menu> filtering(List<Menu> menus, String sort) {
        if (sort.equals("recent")) {
            return menus.stream();
        } else if (sort.equals("likes")) {
            return menus.stream()
                .sorted((menu1, menu2) -> Integer.compare(menu2.getLikeCounts(),
                    menu1.getLikeCounts()));
        } else {
            throw new InvalidInputException(MenuErrorCode.INVALID_INPUT);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<MenuSimpleResponseDto> getLikeMenus(final User user) {
        List<MenuLike> menuLikes = menuLikeRepository.findByUserId(user.getId());

        return menuLikes.stream()
            .map(menuLike -> new MenuSimpleResponseDto(menuLike.getMenu()))
            .toList();
    }
}
