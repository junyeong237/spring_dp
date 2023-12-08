package com.example.dp.domain.user.dto.response;

import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.UserStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String username;
    private final UserRole role;
    private final UserStatus status;
    private final String imageName;
    private final String imagePath;
    private final LocalDateTime createdAt;

    @Builder
    private UserResponseDto(final Long id, final String username, final UserRole role,
        final UserStatus status, final String imageName, final String imagePath,
        final LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.status = status;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
    }
}
