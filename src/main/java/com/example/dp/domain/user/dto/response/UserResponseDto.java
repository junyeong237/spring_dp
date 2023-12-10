package com.example.dp.domain.user.dto.response;

import com.example.dp.domain.user.entity.UserRole;
import com.example.dp.domain.user.entity.UserStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String username;
    private final String introduceMessage;
    private final UserRole role;
    private final UserStatus status;
    private final String imageName;
    private final String imagePath;
    private final LocalDateTime createdAt;

    @Builder
    private UserResponseDto(Long id, String username, String introduceMessage, UserRole role,
        UserStatus status, String imageName, String imagePath, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.introduceMessage = introduceMessage;
        this.role = role;
        this.status = status;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
    }
}
