package com.example.dp.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCheckCodeResponseDto {

    private Boolean isChecked;
    private String message;
    @Builder
    public UserCheckCodeResponseDto(Boolean isChecked, String message) {
        this.isChecked = isChecked;
        this.message = message;
    }

}
