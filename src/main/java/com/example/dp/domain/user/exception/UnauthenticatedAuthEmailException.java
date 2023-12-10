package com.example.dp.domain.user.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class UnauthenticatedAuthEmailException extends RestApiException {

    public UnauthenticatedAuthEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
