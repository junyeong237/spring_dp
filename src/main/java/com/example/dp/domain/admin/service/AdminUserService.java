package com.example.dp.domain.admin.service;

import com.example.dp.domain.user.dto.response.UserResponseDto;

public interface AdminUserService {

    UserResponseDto getUser(Long userId);
}
