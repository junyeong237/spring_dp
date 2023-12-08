package com.example.dp.domain.category.exception;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.code.ErrorCode;

public class ForbiddenDeleteCategory extends RestApiException {

    public ForbiddenDeleteCategory(final ErrorCode errorCode) {
        super(errorCode);
    }
}
