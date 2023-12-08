package com.example.dp.domain.menu.controller;

import com.example.dp.domain.menu.dto.response.MenuSimpleResponseDto;
import com.example.dp.domain.menu.service.MenuService;
import com.example.dp.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuSimpleResponseDto>> getAllMenus(
        @RequestParam(name = "category", defaultValue = "") String categoryType,
        @RequestParam(name = "name", defaultValue = "") String menuName,
        @RequestParam(name = "sort") String sort
    ) {
        List<MenuSimpleResponseDto> responseDto = menuService.getMenus(categoryType, menuName,sort);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/like")
    public ResponseEntity<List<MenuSimpleResponseDto>> getLikeMenus(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<MenuSimpleResponseDto> responseDto = menuService.getLikeMenus(userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }
}
