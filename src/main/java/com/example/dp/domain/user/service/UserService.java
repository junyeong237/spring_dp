package com.example.dp.domain.user.service;

import com.example.dp.domain.user.dto.request.UserCheckCodeRequestDto;
import com.example.dp.domain.user.dto.request.UserIntroduceMessageUpdateRequestDto;
import com.example.dp.domain.user.dto.request.UserPasswordUpdateRequestDto;
import com.example.dp.domain.user.dto.request.UserSendMailRequestDto;
import com.example.dp.domain.user.dto.request.UserSignupRequestDto;
import com.example.dp.domain.user.dto.request.UsernameUpdateRequestDto;
import com.example.dp.domain.user.dto.response.UserCheckCodeResponseDto;
import com.example.dp.domain.user.dto.response.UserIntroduceMessageUpdateResponseDto;
import com.example.dp.domain.user.dto.response.UserResponseDto;
import com.example.dp.domain.user.dto.response.UsernameUpdateResponseDto;
import com.example.dp.domain.user.entity.User;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void signup(UserSignupRequestDto requestDto);

    void sendMail(UserSendMailRequestDto requestDto);

    UserCheckCodeResponseDto checkCode(UserCheckCodeRequestDto requestDto);

    UserResponseDto updateProfileImage(MultipartFile multipartFile, User user) throws IOException;

    void deleteProfileImage(User user);

    boolean userIsBlocked(String email);

    UserResponseDto getProfile(Long userId);

    UsernameUpdateResponseDto updateUsername(UsernameUpdateRequestDto requestDto, User user);

    UserIntroduceMessageUpdateResponseDto updateIntroduceMessage(UserIntroduceMessageUpdateRequestDto requestDto, User user);

    void updatePassword(UserPasswordUpdateRequestDto requestDto, User user);
}
