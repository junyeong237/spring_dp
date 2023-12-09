package com.example.dp.domain.user;

import lombok.Getter;

@Getter
public enum UserStatus {

    ACTIVE("활성화"),
    BLOCKED("차단");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }
}
