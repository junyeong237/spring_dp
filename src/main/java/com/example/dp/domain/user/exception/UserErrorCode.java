package com.example.dp.domain.user.exception;

import com.example.dp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    EXISTS_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 유저이름입니다."),
    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    BLOCK_USER(HttpStatus.UNAUTHORIZED, "차단된 사용자입니다."),
    UNAUTHORIZED_EMAIL(HttpStatus.UNAUTHORIZED, "인증 되지 않은 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다."),
    PASSWORD_RESTRICTION(HttpStatus.BAD_REQUEST, "최근 3번안에 사용한 패스워드 입니다."),
    UNAUTHENTICATED_EMAIL(HttpStatus.UNAUTHORIZED, "인증 코드가 발급되지 않은 이메일입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
