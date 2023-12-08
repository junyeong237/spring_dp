package com.example.dp.domain.cart.exception;

import com.example.dp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CartErrorCode implements ErrorCode {
    NOT_FOUND_CART_MENU(HttpStatus.NOT_FOUND, "삭제하려는 메뉴에 대한 장바구니가 없습니다."),
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "찾으려는 메뉴가 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
