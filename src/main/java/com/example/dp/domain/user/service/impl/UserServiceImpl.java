package com.example.dp.domain.user.service.impl;

import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.UserStatus;
import com.example.dp.domain.user.dto.UserCheckCodeRequestDto;
import com.example.dp.domain.user.dto.UserSendMailRequestDto;
import com.example.dp.domain.user.dto.request.UserSignupRequestDto;
import com.example.dp.domain.user.dto.response.UserResponseDto;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.exception.ExistsUserEmailException;
import com.example.dp.domain.user.exception.NotFoundUserException;
import com.example.dp.domain.user.exception.UserErrorCode;
import com.example.dp.domain.user.repository.UserRepository;
import com.example.dp.domain.user.service.UserService;
import com.example.dp.global.infra.mail.service.impl.MailServiceImpl;
import com.example.dp.global.s3.AwsS3Util;
import com.example.dp.global.s3.AwsS3Util.ImagePath;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailServiceImpl mailService;
    private final PasswordEncoder passwordEncoder;
    private final AwsS3Util awsS3Util;

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

    @Override
    @Transactional
    public UserResponseDto updateProfileImage(final MultipartFile multipartFile, final User user)
        throws IOException {
        User findUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));

        String userImageName = findUser.getImageName();
        if (userImageName != null) {
            awsS3Util.deleteImage(userImageName, ImagePath.PROFILE);
        }

        String imageName = awsS3Util.uploadImage(multipartFile, ImagePath.PROFILE);
        String imagePath = awsS3Util.getImagePath(imageName, ImagePath.PROFILE);
        findUser.updateImage(imageName, imagePath);
        return toDto(findUser);
    }

    @Override
    @Transactional
    public void deleteProfileImage(final User user) {
        User findUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));

        String userImageName = findUser.getImageName();
        if (awsS3Util.existsImage(userImageName, ImagePath.PROFILE)) {
            awsS3Util.deleteImage(userImageName, ImagePath.PROFILE);
        }
        findUser.updateImage(null, null);
    }

    @Override
    public boolean userIsBlocked(final String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
        return user.getStatus() == UserStatus.BLOCKED;
    }

    private UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .role(user.getRole())
            .status(user.getStatus())
            .imageName(user.getImageName())
            .imagePath(user.getImagePath())
            .createdAt(user.getCreatedAt()).build();
    }
}
