package com.example.dp.global.s3.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class AwsS3InternalException extends RestApiException {

    public AwsS3InternalException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
