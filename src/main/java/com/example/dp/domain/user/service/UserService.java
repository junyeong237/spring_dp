package com.example.dp.domain.user.service;

import com.example.dp.domain.user.dto.request.UserSignupRequestDto;

public interface UserService {
    void signup(UserSignupRequestDto requestDto);
}
