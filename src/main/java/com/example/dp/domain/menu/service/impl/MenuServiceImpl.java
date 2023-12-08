package com.example.dp.domain.menu.service.impl;

import com.example.dp.domain.menu.dto.response.MenuSimpleResponseDto;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.menu.service.MenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public List<MenuSimpleResponseDto> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();

        return menus.stream()
            .map(MenuSimpleResponseDto::new)
            .toList();
    }
}
