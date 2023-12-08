package com.example.dp.domain.user.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class NotFoundUserException extends RestApiException {

    public NotFoundUserException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
