package com.example.dp.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordHistoryResponseDto {
    private String password;

    @Builder
    private PasswordHistoryResponseDto(String password) {
        this.password = password;
    }

    public static PasswordHistoryResponseDto of(String password) {
        return PasswordHistoryResponseDto.builder()
            .password(password)
            .build();
    }
}
