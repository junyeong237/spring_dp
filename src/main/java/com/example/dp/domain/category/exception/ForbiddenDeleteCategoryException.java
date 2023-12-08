package com.example.dp.domain.category.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class ForbiddenDeleteCategoryException extends RestApiException {

    public ForbiddenDeleteCategoryException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
