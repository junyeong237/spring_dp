package com.example.dp.domain.user.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class ExistsUsernameException extends RestApiException {

    public ExistsUsernameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
