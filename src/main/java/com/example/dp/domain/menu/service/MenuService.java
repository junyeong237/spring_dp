package com.example.dp.domain.menu.service;

import com.example.dp.domain.menu.dto.response.MenuSimpleResponseDto;
import java.util.List;

public interface MenuService {

    List<MenuSimpleResponseDto> getMenus(String categoryType, String menuName);
}
