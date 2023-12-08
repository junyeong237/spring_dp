package com.example.dp.domain.order.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class AlreadyOrderStateFinishedException extends RestApiException {

    public AlreadyOrderStateFinishedException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
