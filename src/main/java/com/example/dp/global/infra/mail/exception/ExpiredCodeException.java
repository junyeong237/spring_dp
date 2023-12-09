package com.example.dp.global.infra.mail.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class ExpiredCodeException extends RestApiException {

    public ExpiredCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
