package com.example.dp.domain.order.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class ForbiddenDeleteOrderStateException extends RestApiException {

    public ForbiddenDeleteOrderStateException(final ErrorCode errorCode) {
        super(errorCode);
    }

}
