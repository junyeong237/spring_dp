package com.example.dp.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsernameUpdateResponseDto {

    private String username;

    @Builder
    private UsernameUpdateResponseDto(String username) {
        this.username = username;
    }

    public static UsernameUpdateResponseDto of(String username) {
        return UsernameUpdateResponseDto.builder()
            .username(username)
            .build();
    }
}
