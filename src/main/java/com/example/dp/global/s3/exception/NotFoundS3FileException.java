package com.example.dp.global.s3.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class NotFoundS3FileException extends RestApiException {

    public NotFoundS3FileException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
