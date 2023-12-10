package com.example.dp.global.s3.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class FileTypeNotAllowedException extends RestApiException {

    public FileTypeNotAllowedException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
