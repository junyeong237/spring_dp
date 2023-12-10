package com.example.dp.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPasswordUpdateResponseDto {

    private String password;

    @Builder
    private UserPasswordUpdateResponseDto(String password) {
        this.password = password;
    }

    public static UserPasswordUpdateResponseDto of(String password) {
        return UserPasswordUpdateResponseDto.builder()
            .password(password)
            .build();
    }
}
