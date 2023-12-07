package com.example.dp.domain.admin.service.impl;

import com.example.dp.domain.admin.service.AdminMenuService;
import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.category.repository.CategoryRepository;
import com.example.dp.domain.menu.dto.request.MenuRequestDto;
import com.example.dp.domain.menu.dto.response.MenuDetailResponseDto;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.exception.NotFoundMenuException;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.menucategory.entity.MenuCategory;
import com.example.dp.domain.menucategory.repository.MenuCategoryRepository;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminMenuServiceImpl implements AdminMenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final MenuCategoryRepository menuCategoryRepository;


    @Transactional
    @Override
    public MenuDetailResponseDto createMenu(final MenuRequestDto requestDto) {

        //메뉴를 생성합니다.
        Menu menu = Menu.builder()
            .name(requestDto.getName())
            .description(requestDto.getDescription())
            .price(requestDto.getPrice())
            .quantity(requestDto.getQuantity())
            .status(requestDto.getStatus())
            .build();

        menu = menuRepository.save(menu);

        addCategory(requestDto, menu);

        return new MenuDetailResponseDto(menu);
    }
    
    private void addCategory(MenuRequestDto requestDto, Menu menu) {
        // 카테고리들이 각각 존재하는 카테고리인지 확인
        List<Category> categoryList = requestDto.getCategoryNameList().stream()
            .map(categoryType ->
                categoryRepository.findByType(categoryType)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다.")))
            .toList();

        // 메뉴카테고리 테이블에 연관관계 저장
        categoryList.forEach(category -> {
            MenuCategory menuCategory = MenuCategory.builder()
                .category(category)
                .menu(menu)
                .build();
            menuCategoryRepository.save(menuCategory);
            menu.addMenuCategory(menuCategory);
        });
    }

    @Override
    @Transactional
    public MenuDetailResponseDto updateMenu(
        final Long menuId,
        final MenuRequestDto menuRequestDto) {
        Menu menu = findMenu(menuId);
        menu.update(menuRequestDto);
        return new MenuDetailResponseDto(menu);
    }

    @Override
    public void deleteMenu(final Long menuId) {
        Menu menu = findMenu(menuId);
        menuRepository.delete(menu);
    }

    @Override
    public MenuDetailResponseDto getAdminMenu(final Long menuId) {
        Menu menu = findMenu(menuId);
        return new MenuDetailResponseDto(menu);
    }

    @Override
    public List<MenuDetailResponseDto> getAdminMenus() {
        List<Menu> menus = menuRepository.findByOrderByCreatedAt();
        return menus.stream().map(MenuDetailResponseDto::new).toList();
    }

    public Menu findMenu(Long menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(NotFoundMenuException::new);
    }

}
