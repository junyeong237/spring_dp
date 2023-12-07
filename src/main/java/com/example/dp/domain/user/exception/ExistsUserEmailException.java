package com.example.dp.domain.user.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class ExistsUserEmailException extends RestApiException {

    public ExistsUserEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
