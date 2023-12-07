package com.example.dp.domain.menu.exception;

import com.example.dp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {

    EXISTS_MENU_NAME(HttpStatus.BAD_REQUEST, "이미 존재하는 메뉴이름입니다."),
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "존재하지 않는 메뉴입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
