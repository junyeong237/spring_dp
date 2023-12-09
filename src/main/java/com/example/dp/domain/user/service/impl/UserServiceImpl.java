package com.example.dp.domain.user.service.impl;

import static com.example.dp.domain.user.constant.UserConstant.FAIL_CHECK_CODE_MESSAGE;
import static com.example.dp.domain.user.constant.UserConstant.NAME;
import static com.example.dp.domain.user.constant.UserConstant.SUBJECT;
import static com.example.dp.domain.user.constant.UserConstant.SUCCESS_CHECK_CODE_MESSAGE;

import com.example.dp.domain.authemail.service.impl.AuthEmailServiceImpl;
import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.UserStatus;
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
import com.example.dp.domain.user.exception.ExistsUserEmailException;
import com.example.dp.domain.user.exception.NotFoundUserException;
import com.example.dp.domain.user.exception.UnauthorizedEmailException;
import com.example.dp.domain.user.exception.UserErrorCode;
import com.example.dp.domain.user.exception.VerifyPasswordException;
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
    private final AuthEmailServiceImpl authEmailService;
    private final PasswordEncoder passwordEncoder;
    private final AwsS3Util awsS3Util;

    @Override
    public UserResponseDto getProfile(Long userId) {
        User user = getFindUser(userId);

        return toDto(user);
    }

    @Override
    public void signup(final UserSignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ExistsUserEmailException(UserErrorCode.EXISTS_EMAIL);
        }
        if (!authEmailService.getAuthEmailIsChecked(request.getEmail())) {
            throw new UnauthorizedEmailException(UserErrorCode.UNAUTHORIZED_EMAIL);
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
        authEmailService.createAuthEmail(requestDto.getEmail());
    }

    @Override
    public UserCheckCodeResponseDto checkCode(UserCheckCodeRequestDto requestDto) {
        boolean isChecked = mailService.checkCode(requestDto.getEmail(), requestDto.getCode());
        String message = FAIL_CHECK_CODE_MESSAGE;
        if (isChecked) {
            message = SUCCESS_CHECK_CODE_MESSAGE;
            authEmailService.completedAuth(requestDto.getEmail());
        }
        return UserCheckCodeResponseDto.builder()
            .isChecked(isChecked)
            .message(message)
            .build();
    }

    @Override
    @Transactional
    public UsernameUpdateResponseDto updateUsername(UsernameUpdateRequestDto requestDto,
        User user) {
        User findUser = getFindUser(user.getId());

        findUser.updateUsername(requestDto.getUsername());

        return UsernameUpdateResponseDto.of(findUser.getUsername());
    }

    @Override
    @Transactional
    public UserIntroduceMessageUpdateResponseDto updateIntroduceMessage(
        UserIntroduceMessageUpdateRequestDto requestDto, User user) {
        User findUser = getFindUser(user.getId());

        findUser.updateIntroduceMessage(requestDto.getIntroduceMessage());

        return UserIntroduceMessageUpdateResponseDto.of(findUser.getIntroduceMessage());
    }

    @Override
    @Transactional
    public void updatePassword(UserPasswordUpdateRequestDto requestDto,
        User user) {
        User findUser = getFindUser(user.getId());

        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new VerifyPasswordException(UserErrorCode.PASSWORD_MISMATCH);
        }
        String encodePassword = passwordEncoder.encode(requestDto.getNewPassword());
        findUser.updatePassword(encodePassword);
    }

    @Override
    @Transactional
    public UserResponseDto updateProfileImage(final MultipartFile multipartFile, final User user)
        throws IOException {
        User findUser = getFindUser(user.getId());

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
        User findUser = getFindUser(user.getId());

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

    private User getFindUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
    }

    private UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .introduceMessage(user.getIntroduceMessage())
            .role(user.getRole())
            .status(user.getStatus())
            .imageName(user.getImageName())
            .imagePath(user.getImagePath())
            .createdAt(user.getCreatedAt()).build();
    }
}
