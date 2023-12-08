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
    ALREADY_ORDER_STATE_CANCEL(HttpStatus.NOT_FOUND, "이미 주문 취소된 상태입니다."),
    ALREADY_ORDER_STATE_FINISHED(HttpStatus.NOT_FOUND, "이미 주문 완료된 상태입니다."),
    FORBIDDEN_DELETE_ORDER_STATE(HttpStatus.FORBIDDEN, "주문을 취소 할 수 없는 상태입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
