package com.example.dp.domain.admin.service;

import com.example.dp.domain.menu.dto.response.MenuDetailResponseDto;
import com.example.dp.domain.menu.dto.request.MenuRequestDto;
import java.util.List;

public interface AdminMenuService {

    MenuDetailResponseDto createMenu(MenuRequestDto menuRequestDto);

    MenuDetailResponseDto updateMenu(Long menuId, MenuRequestDto menuRequestDto);

    void deleteMenu(Long menuId);

    MenuDetailResponseDto getAdminMenu(Long menuId);

    List<MenuDetailResponseDto> getAdminMenus();
}
