package com.example.dp.domain.category.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class ExistsCategoryTypeException extends RestApiException {
    public ExistsCategoryTypeException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
