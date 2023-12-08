package com.example.dp.domain.cart.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class NotFoundCartMenuExcepiton extends RestApiException {

    public NotFoundCartMenuExcepiton(final ErrorCode errorCode) {
        super(errorCode);
    }

}
