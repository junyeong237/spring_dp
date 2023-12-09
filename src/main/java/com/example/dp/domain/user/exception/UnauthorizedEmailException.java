package com.example.dp.domain.user.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class UnauthorizedEmailException extends RestApiException {
    public UnauthorizedEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
