package com.example.dp.domain.menu.controller;

import com.example.dp.domain.menu.dto.request.MenuRequestDto;
import com.example.dp.domain.menu.dto.response.MenuDetailResponseDto;
import com.example.dp.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/admin/menus")
    public MenuDetailResponseDto createMenu(@RequestBody MenuRequestDto menuRequestDto) {
        return menuService.createMenu(menuRequestDto);
    }

    @PatchMapping("/admin/menus/{menuId}")
    public MenuDetailResponseDto updateMenu(
        @PathVariable Long menuId,
        @RequestBody MenuRequestDto menuRequestDto) {
        return menuService.updateMenu(menuId, menuRequestDto);
    }
}
