package com.example.dp.domain.admin.service;

import com.example.dp.domain.menu.dto.request.MenuRequestDto;
import com.example.dp.domain.menu.dto.response.MenuDetailResponseDto;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AdminMenuService {

    MenuDetailResponseDto createMenu(MultipartFile multipartFile, MenuRequestDto menuRequestDto)
        throws IOException;

    MenuDetailResponseDto updateMenu(Long menuId, MenuRequestDto menuRequestDto);

    void deleteMenu(Long menuId);

    MenuDetailResponseDto getAdminMenu(Long menuId);

    List<MenuDetailResponseDto> getAdminMenus(String sort);
}
