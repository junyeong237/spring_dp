package com.example.dp.domain.user.dto.response;

import com.example.dp.domain.user.UserRole;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String username;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public UserResponseDto(final Long id, final String username, final UserRole role,
        final LocalDateTime createdAt,
        final LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
