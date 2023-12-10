package com.example.dp.global.security;

import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        return new UserDetailsImpl(user);
    }

    public UserDetailsImpl loadUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NullPointerException("존재하지 않는 유저 아이디입니다."));

        return new UserDetailsImpl(user);
    }

}
