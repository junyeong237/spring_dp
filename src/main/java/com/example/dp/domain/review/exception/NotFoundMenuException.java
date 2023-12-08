package com.example.dp.domain.review.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class NotFoundMenuException extends RestApiException {

    public NotFoundMenuException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
