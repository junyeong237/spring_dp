package com.example.dp.domain.menu.service.impl;

import com.example.dp.domain.menu.dto.request.MenuRequestDto;
import com.example.dp.domain.menu.dto.response.MenuDetailResponseDto;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.exception.NotFoundMenuException;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public MenuDetailResponseDto createMenu(final MenuRequestDto menuRequestDto) {
        Menu menu = Menu.builder()
            .name(menuRequestDto.getName())
            .description(menuRequestDto.getDescription())
            .price(menuRequestDto.getPrice())
            .quantity(menuRequestDto.getQuantity())
            .status(menuRequestDto.getStatus())
            .build();
        menu = menuRepository.save(menu);
        return new MenuDetailResponseDto(menu);
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
        //TODO: admin 메뉴 단건 조회
        return null;
    }

    @Override
    public MenuDetailResponseDto getAdminMenus() {
        //TODO: admin 메뉴 전체 조회
        return null;
    }

    public Menu findMenu(Long menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(NotFoundMenuException::new);
    }

}
