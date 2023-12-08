package com.example.dp.domain.user.service;

import com.example.dp.domain.user.dto.UserCheckCodeRequestDto;
import com.example.dp.domain.user.dto.UserSendMailRequestDto;
import com.example.dp.domain.user.dto.request.UserSignupRequestDto;

public interface UserService {

    void signup(UserSignupRequestDto requestDto);
    void sendMail(UserSendMailRequestDto requestDto);
    Boolean checkCode(UserCheckCodeRequestDto requestDto);
}
