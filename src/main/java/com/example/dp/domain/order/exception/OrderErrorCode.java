package com.example.dp.domain.order.exception;

import com.example.dp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),
    FORBIDDEN_DELETE_ORDER_ROLE(HttpStatus.FORBIDDEN, "주문자나 관리자만 삭제할 수 있습니다."),
    NOT_FOUND_CARTLIST_FOR_ORDER(HttpStatus.NOT_FOUND, "주문하실 장바구니가 없습니다."),
    FORBIDDEN_ORDER_STATE(HttpStatus.NOT_FOUND, "잘못된 주문상태를 입력받았습니다.."),
    FORBIDDEN_ORDER_STATE_NOT_CREATED(HttpStatus.NOT_FOUND, "주문 진행중이 아니라 주문 완료를 누를 수 없습니다."),
    FORBIDDEN_ORDER_STATE_NOT_PENDING(HttpStatus.FORBIDDEN, "주문 보류중이 아닙니다."),
    FORBIDDEN_ORDER_QUANTITY(HttpStatus.FORBIDDEN, "수량이 부족하여 주문하실 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
