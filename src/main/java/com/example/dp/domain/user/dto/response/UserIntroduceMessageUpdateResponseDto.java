package com.example.dp.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserIntroduceMessageUpdateResponseDto {

    private String introduceMessage;

    @Builder
    private UserIntroduceMessageUpdateResponseDto(String introduceMessage) {
        this.introduceMessage = introduceMessage;
    }

    public static UserIntroduceMessageUpdateResponseDto of(String introduceMessage) {
        return UserIntroduceMessageUpdateResponseDto.builder()
            .introduceMessage(introduceMessage)
            .build();
    }
}
