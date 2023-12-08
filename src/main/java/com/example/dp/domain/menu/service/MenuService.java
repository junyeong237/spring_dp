package com.example.dp.domain.menu.service;

import com.example.dp.domain.menu.dto.response.MenuSimpleResponseDto;
import com.example.dp.domain.user.entity.User;
import java.util.List;

public interface MenuService {

    List<MenuSimpleResponseDto> getMenus(String categoryType, String menuName, String sort);

    List<MenuSimpleResponseDto> getLikeMenus(User user);
}
