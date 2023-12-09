package com.example.dp.domain.authemail.service;

public interface AuthEmailService {
    Boolean getAuthEmailIsChecked(String email);
    void createAuthEmail(String email);
    void completedAuth(String email);
}
