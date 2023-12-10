package com.example.dp.domain.admin.service.impl;

import com.example.dp.domain.admin.service.AdminUserService;
import com.example.dp.domain.user.entity.UserRole;
import com.example.dp.domain.user.entity.UserStatus;
import com.example.dp.domain.user.dto.response.UserResponseDto;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.exception.NotFoundUserException;
import com.example.dp.domain.user.exception.UserErrorCode;
import com.example.dp.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUser(final Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));

        return toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findByOrderByCreatedAt();
        return users
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    @Transactional
    public UserResponseDto grantUserRole(final Long userId) {
        // TODO: 관리자 -> 사용자로 등급관리 가능하게 할지 고려
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
        user.updateUserRole(UserRole.ADMIN);
        return toDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto blockUser(final Long userId) {
        // TODO: 관리자 -> 차단을 해제해주는 기능 구현할지 고려
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
        user.updateUserStatus(UserStatus.BLOCKED);
        return toDto(user);
    }

    private UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .role(user.getRole())
            .status(user.getStatus())
            .imagePath(user.getImagePath())
            .imageName(user.getImageName())
            .createdAt(user.getCreatedAt()).build();
    }
}
