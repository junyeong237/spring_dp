package com.example.dp.domain.user.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class BlockedUserException extends RestApiException {

    public BlockedUserException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
