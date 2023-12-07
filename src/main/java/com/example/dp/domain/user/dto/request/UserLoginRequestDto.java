package com.example.dp.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "유저 이름은 영 소문자, 숫자를 포함한 4글자 이상 10글자 이하입니다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 영문자, 숫자를 포함한 8글자 이상 15글자 이하입니다.")
    private String password;
}
