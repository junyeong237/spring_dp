package com.example.dp.domain.menu.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class InvalidInputException extends RestApiException {

    public InvalidInputException(final ErrorCode errorCode) {
        super(errorCode);
    }
}

