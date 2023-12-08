package com.example.dp.domain.user.service;

import com.example.dp.domain.user.dto.UserCheckCodeRequestDto;
import com.example.dp.domain.user.dto.UserSendMailRequestDto;
import com.example.dp.domain.user.dto.request.UserSignupRequestDto;
import com.example.dp.domain.user.dto.response.UserResponseDto;
import com.example.dp.domain.user.entity.User;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void signup(UserSignupRequestDto requestDto);

    void sendMail(UserSendMailRequestDto requestDto);

    Boolean checkCode(UserCheckCodeRequestDto requestDto);

    UserResponseDto updateProfileImage(MultipartFile multipartFile, User user) throws IOException;

    void deleteProfileImage(User user);
}
