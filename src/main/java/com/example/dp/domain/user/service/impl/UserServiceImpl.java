package com.example.dp.domain.user.service.impl;

import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.dto.request.UserSignupRequestDto;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.exception.ExistsUserEmailException;
import com.example.dp.domain.user.exception.ExistsUsernameException;
import com.example.dp.domain.user.exception.UserErrorCode;
import com.example.dp.domain.user.repository.UserRepository;
import com.example.dp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(final UserSignupRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ExistsUsernameException(UserErrorCode.EXISTS_USERNAME);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ExistsUserEmailException(UserErrorCode.EXISTS_EMAIL);
        }

        String encryptionPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(encryptionPassword)
            .role(UserRole.USER)
            .build();

        userRepository.save(user);
    }
}
