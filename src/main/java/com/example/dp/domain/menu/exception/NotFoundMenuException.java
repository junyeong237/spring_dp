package com.example.dp.domain.menu.exception;

public class NotFoundMenuException extends RuntimeException {

    public NotFoundMenuException() {
        super("존재하지 않는 Menu입니다.");
    }
}
