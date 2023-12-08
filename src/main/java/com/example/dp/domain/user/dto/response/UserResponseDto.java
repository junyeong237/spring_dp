package com.example.dp.domain.user.dto.response;

import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.UserStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String username;
    private UserRole role;
    private UserStatus status;
    private String imageName;
    private String imagePath;
    private LocalDateTime createdAt;

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
