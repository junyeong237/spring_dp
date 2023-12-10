package com.example.dp.domain.order.entity;


import lombok.Getter;

@Getter
public enum OrderState {

    PENDING("주문 보류중"),
    CREATED("주문 진행중"),
    COMPLETED("주문 완료"),
    CANCELLED("주문 거절");

    private final String description;

    OrderState(String description) {
        this.description = description;
    }
}
