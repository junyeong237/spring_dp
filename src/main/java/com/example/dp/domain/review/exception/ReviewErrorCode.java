package com.example.dp.domain.review.exception;

import com.example.dp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다."),
    FORBIDDEN_CREATE(HttpStatus.BAD_REQUEST, "완료된 주문에만 리뷰를 작성할 수 있습니다.."),
    FORBIDDEN_ACCESS(HttpStatus.BAD_REQUEST, "주문자만 리뷰를 작성/수정할 수 있습니다."),
    EXISTS_REVIEW(HttpStatus.BAD_REQUEST, "이미 리뷰가 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
