package com.example.dp.domain.category.exception;

import com.example.dp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {

    EXISTS_CATEGORY_TYPE(HttpStatus.BAD_REQUEST, "이미 존재하는 카테고리입니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다"),
    FORBIDEN_DELETE(HttpStatus.BAD_REQUEST, "카테고리에 등록된 메뉴가 있어 삭제할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
