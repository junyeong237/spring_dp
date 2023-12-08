package com.example.dp.domain.review.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class NotFoundReviewException extends RestApiException {

    public NotFoundReviewException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
