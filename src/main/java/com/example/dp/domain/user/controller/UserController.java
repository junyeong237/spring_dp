package com.example.dp.domain.user.controller;

import com.example.dp.domain.user.dto.request.UserCheckCodeRequestDto;
import com.example.dp.domain.user.dto.request.UserSendMailRequestDto;
import com.example.dp.domain.user.dto.request.UserSignupRequestDto;
import com.example.dp.domain.user.dto.response.UserCheckCodeResponseDto;
import com.example.dp.domain.user.dto.response.UserResponseDto;
import com.example.dp.domain.user.service.impl.UserServiceImpl;
import com.example.dp.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserSignupRequestDto request) {
        userService.signup(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signup/mail")
    public ResponseEntity<Void> sendMail(@RequestBody @Valid UserSendMailRequestDto request) {
        userService.sendMail(request);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signup/mail/code")
    public ResponseEntity<UserCheckCodeResponseDto> checkCode(@RequestBody @Valid UserCheckCodeRequestDto request) {

        return ResponseEntity.ok(userService.checkCode(request));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponseDto> updateProfileImage(
        @RequestPart("image") MultipartFile multipartFile,
        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        UserResponseDto responseDto = userService.updateProfileImage(multipartFile,
            userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<UserResponseDto> deleteProfileImage(
        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        userService.deleteProfileImage(userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}

