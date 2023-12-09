package com.example.dp.domain.authemail.exception;

import com.example.dp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthEmailErrorCode implements ErrorCode {
    UNAUTHENTICATED_EMAIL(HttpStatus.UNAUTHORIZED, "인증 코드가 발급되지 않은 이메일입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
