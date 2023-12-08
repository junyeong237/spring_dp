package com.example.dp.domain.admin.controller;

import com.example.dp.domain.admin.service.AdminUserService;
import com.example.dp.domain.user.dto.response.UserResponseDto;
import com.example.dp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId){
        UserResponseDto responseDto = adminUserService.getUser(userId);
        return ResponseEntity.ok(responseDto);
    }

}
