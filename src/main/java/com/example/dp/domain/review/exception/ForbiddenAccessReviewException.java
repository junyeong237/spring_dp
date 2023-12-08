package com.example.dp.domain.review.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class ForbiddenAccessReviewException extends RestApiException {

    public ForbiddenAccessReviewException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
