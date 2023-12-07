package com.example.dp.global.security;

import static com.example.dp.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static com.example.dp.global.jwt.JwtUtil.BEARER_PREFIX;
import static com.example.dp.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

import com.example.dp.global.redis.RedisUtil;
import com.example.dp.global.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
        FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.getTokenFromHeader(req, ACCESS_TOKEN_HEADER);

        if (StringUtils.hasText(accessToken) && redisUtil.containBlackList(accessToken)) {
            accessToken = null;
        }

        if (StringUtils.hasText(accessToken) && !jwtUtil.validateToken(accessToken)) {
            String refreshToken = jwtUtil.getTokenFromHeader(req, REFRESH_TOKEN_HEADER);
            if (StringUtils.hasText(refreshToken) && jwtUtil.validateToken(refreshToken)
                && redisUtil.hasKey(refreshToken)) {
                Long userId = (Long) redisUtil.get(refreshToken);
                UserDetailsImpl userDetails = userDetailsService.loadUserById(userId);
                accessToken = jwtUtil.createAccessToken(
                        userDetails.getUsername(),
                        userDetails.getUser().getRole().getAuthority()
                    )
                    .split(" ")[1].trim();
                res.addHeader("AccessToken", BEARER_PREFIX + accessToken);
            }
        }

        if (StringUtils.hasText(accessToken)) {
            Claims info = jwtUtil.getUserInfoFromToken(accessToken);
            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(req, res);
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }
}