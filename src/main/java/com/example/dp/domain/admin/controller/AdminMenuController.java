package com.example.dp.domain.admin.controller;

import com.example.dp.domain.menu.dto.request.MenuRequestDto;
import com.example.dp.domain.menu.dto.response.MenuDetailResponseDto;
import com.example.dp.domain.admin.service.AdminMenuService;
import java.util.List;
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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @PostMapping("/menus")
    public ResponseEntity<MenuDetailResponseDto> createMenu(
        @RequestBody MenuRequestDto menuRequestDto) {
        MenuDetailResponseDto responseDto = adminMenuService.createMenu(menuRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/menus/{menuId}")
    public ResponseEntity<MenuDetailResponseDto> updateMenu(
        @PathVariable Long menuId,
        @RequestBody MenuRequestDto menuRequestDto) {
        MenuDetailResponseDto responseDto = adminMenuService.updateMenu(menuId, menuRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long menuId) {
        adminMenuService.deleteMenu(menuId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/menus/{menuId}")
    public ResponseEntity<MenuDetailResponseDto> getAdminMenu(@PathVariable Long menuId) {
        MenuDetailResponseDto responseDto = adminMenuService.getAdminMenu(menuId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/menus")
    public ResponseEntity<List<MenuDetailResponseDto>> getAdminMenus() {
        List<MenuDetailResponseDto> responseDto = adminMenuService.getAdminMenus();
        return ResponseEntity.ok(responseDto);
    }
}
