package com.example.dp.domain.menu.controller;

import com.example.dp.domain.menu.dto.request.MenuRequestDto;
import com.example.dp.domain.menu.dto.response.MenuDetailResponseDto;
import com.example.dp.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<MenuDetailResponseDto> createMenu(@RequestBody MenuRequestDto menuRequestDto) {
         MenuDetailResponseDto responseDto = menuService.createMenu(menuRequestDto);
         return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/admin/menus/{menuId}")
    public ResponseEntity<MenuDetailResponseDto> updateMenu(
        @PathVariable Long menuId,
        @RequestBody MenuRequestDto menuRequestDto) {
        MenuDetailResponseDto responseDto = menuService.updateMenu(menuId, menuRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/admin/menus/{menuId}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long menuId){
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/menus/{menuId}")
    public ResponseEntity<MenuDetailResponseDto> getAdminMenu(@PathVariable Long menuId) {
        MenuDetailResponseDto responseDto = menuService.getAdminMenu(menuId);
        return ResponseEntity.ok(responseDto);
    }
}
