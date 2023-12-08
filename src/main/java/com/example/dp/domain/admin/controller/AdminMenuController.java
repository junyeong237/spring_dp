package com.example.dp.domain.admin.controller;

import com.example.dp.domain.admin.service.AdminMenuService;
import com.example.dp.domain.menu.dto.request.MenuRequestDto;
import com.example.dp.domain.menu.dto.response.MenuDetailResponseDto;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @PostMapping("/menus")
    public ResponseEntity<MenuDetailResponseDto> createMenu(
        @RequestPart MultipartFile multipartFile,
        @RequestPart MenuRequestDto menuRequestDto) throws IOException {
        MenuDetailResponseDto responseDto = adminMenuService.createMenu(multipartFile, menuRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/menus/{menuId}")
    public ResponseEntity<MenuDetailResponseDto> updateMenu(
        @PathVariable Long menuId,
        @RequestBody MenuRequestDto menuRequestDto) {
        MenuDetailResponseDto responseDto = adminMenuService.updateMenu(menuId, menuRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        adminMenuService.deleteMenu(menuId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/menus/{menuId}")
    public ResponseEntity<MenuDetailResponseDto> getAdminMenu(@PathVariable Long menuId) {
        MenuDetailResponseDto responseDto = adminMenuService.getAdminMenu(menuId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/menus")
    public ResponseEntity<List<MenuDetailResponseDto>> getAdminMenus(
        @RequestParam(name="sort") String sort
    ) {
        List<MenuDetailResponseDto> responseDto = adminMenuService.getAdminMenus(sort);
        return ResponseEntity.ok(responseDto);
    }
}
