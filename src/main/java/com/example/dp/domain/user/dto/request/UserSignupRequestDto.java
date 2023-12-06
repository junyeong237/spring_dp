package com.example.dp.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "유저 이름은 영 소문자, 숫자를 포함한 4글자 이상 10글자 이하입니다.")
    private String username;

    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "적절한 이메일 형식이 아닙니다.")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 영문자, 숫자를 포함한 8글자 이상 15글자 이하입니다.")
    private String password;
}
