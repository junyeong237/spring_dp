package com.example.dp.domain.admin.service.impl;

import com.example.dp.domain.admin.service.AdminUserService;
import com.example.dp.domain.review.exception.NotFoundUserException;
import com.example.dp.domain.user.dto.response.UserResponseDto;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.exception.UserErrorCode;
import com.example.dp.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    @Override
    public UserResponseDto getUser(final Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundUserException(
                UserErrorCode.NOT_FOUND_USER));

        return toDto(user);
    }

    private UserResponseDto toDto(User user){
        return UserResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .role(user.getRole())
            .createdAt(user.getCreatedAt()).build();
    }
}
