package com.example.dp.domain.user.service.impl;

import com.example.dp.global.redis.RedisUtil;
import com.example.dp.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLogoutImpl implements LogoutHandler {

    private final RedisUtil redisUtil;

    public final Integer ACCESS_TOKEN_TIME = 30;
    public final String VALUE = "blackToken";

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {
        String accessToken = request.getHeader(JwtUtil.ACCESS_TOKEN_HEADER).split(" ")[1].trim();
        String refreshToken = request.getHeader(JwtUtil.REFRESH_TOKEN_HEADER).split(" ")[1].trim();

        redisUtil.addBlackList(accessToken, VALUE, ACCESS_TOKEN_TIME);
        redisUtil.delete(refreshToken);
    }
}
