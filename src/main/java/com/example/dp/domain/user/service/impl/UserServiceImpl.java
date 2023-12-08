package com.example.dp.domain.user.service.impl;

import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.dto.UserCheckCodeRequestDto;
import com.example.dp.domain.user.dto.UserSendMailRequestDto;
import com.example.dp.domain.user.dto.request.UserSignupRequestDto;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.exception.ExistsUserEmailException;
import com.example.dp.domain.user.exception.UserErrorCode;
import com.example.dp.domain.user.repository.UserRepository;
import com.example.dp.domain.user.service.UserService;
import com.example.dp.global.infra.mail.service.impl.MailServiceImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailServiceImpl mailService;
    private final PasswordEncoder passwordEncoder;

    private static final String SUBJECT = "4족 배달 [이메일 인증]";
    private static final String NAME = "라이더";

    //TODO:: email check 값을 redis value 값으로 저장해 상태 변경을 하는 것 보다는 다른 방법을 고려해보기

    @Override
    public void signup(final UserSignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ExistsUserEmailException(UserErrorCode.EXISTS_EMAIL);
        }

        String encryptionPassword = passwordEncoder.encode(request.getPassword());
        String randomName = NAME + UUID.randomUUID().toString().substring(0, 8);

        while (userRepository.existsByUsername(randomName)) {
            randomName = NAME + UUID.randomUUID().toString().substring(0, 8);
        }

        User user = User.builder()
            .username(randomName)
            .email(request.getEmail())
            .password(encryptionPassword)
            .role(UserRole.USER)
            .build();

        userRepository.save(user);
    }

    @Override
    public void sendMail(UserSendMailRequestDto requestDto) {
        mailService.sendMessage(requestDto.getEmail(), SUBJECT);
    }

    @Override
    public Boolean checkCode(UserCheckCodeRequestDto requestDto) {
        return mailService.checkCode(requestDto.getEmail(), requestDto.getCode());
    }
}
