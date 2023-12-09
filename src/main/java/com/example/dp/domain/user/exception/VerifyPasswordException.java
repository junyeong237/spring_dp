package com.example.dp.domain.user.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class VerifyPasswordException extends RestApiException{

    public VerifyPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
