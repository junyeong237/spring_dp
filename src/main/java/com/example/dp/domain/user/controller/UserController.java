package com.example.dp.domain.user.controller;

import com.example.dp.domain.user.dto.request.UserSignupRequestDto;
import com.example.dp.domain.user.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserSignupRequestDto request) {
        userService.signup(request);
        return ResponseEntity.noContent().build();
        //TODO: 공통 응답 메세지 추가
    }
}

