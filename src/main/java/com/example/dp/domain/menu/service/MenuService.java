package com.example.dp.domain.menu.service;

import com.example.dp.domain.menu.dto.response.MenuDetailResponseDto;
import com.example.dp.domain.menu.dto.request.MenuRequestDto;
import java.util.List;

public interface MenuService {

    MenuDetailResponseDto createMenu(MenuRequestDto menuRequestDto);

    MenuDetailResponseDto updateMenu(Long menuId, MenuRequestDto menuRequestDto);

    void deleteMenu(Long menuId);

    MenuDetailResponseDto getAdminMenu(Long menuId);

    List<MenuDetailResponseDto> getAdminMenus();
}
