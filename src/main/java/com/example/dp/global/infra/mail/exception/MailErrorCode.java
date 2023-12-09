package com.example.dp.global.infra.mail.exception;

import com.example.dp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MailErrorCode implements ErrorCode {
    EXPIRED_CODE(HttpStatus.UNAUTHORIZED, "기간이 만료된 코드입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
