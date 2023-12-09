package com.example.dp.domain.admin.service;

import com.example.dp.domain.user.dto.response.UserResponseDto;
import java.util.List;

public interface AdminUserService {

    UserResponseDto getUser(Long userId);

    List<UserResponseDto> getAllUsers();

    UserResponseDto grantUserRole(Long userId);

    UserResponseDto blockUser(Long userId);
}
