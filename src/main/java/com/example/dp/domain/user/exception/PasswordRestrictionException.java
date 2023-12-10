package com.example.dp.domain.user.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class PasswordRestrictionException extends RestApiException {

    public PasswordRestrictionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
