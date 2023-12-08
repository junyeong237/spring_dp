package com.example.dp.global.s3.exception;

import com.example.dp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AwsS3ErrorCode implements ErrorCode {

    FILE_NOT_ALLOW(HttpStatus.BAD_REQUEST, "허용되지 않는 파일 형식입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
