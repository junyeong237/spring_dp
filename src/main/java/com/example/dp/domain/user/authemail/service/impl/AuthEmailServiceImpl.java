package com.example.dp.domain.user.authemail.service.impl;

import static com.example.dp.domain.user.authemail.exception.AuthEmailErrorCode.UNAUTHENTICATED_EMAIL;

import com.example.dp.domain.user.authemail.entity.AuthEmail;
import com.example.dp.domain.user.authemail.exception.UnauthenticatedAuthEmailException;
import com.example.dp.domain.user.authemail.repository.AuthEmailRepository;
import com.example.dp.domain.user.authemail.service.AuthEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthEmailServiceImpl implements AuthEmailService {

    private final AuthEmailRepository authEmailRepository;

    @Override
    public Boolean getAuthEmailIsChecked(final String email) {
        AuthEmail authEmail = getAuthEmail(email);

        return authEmail.getIsChecked();
    }

    @Override
    public void createAuthEmail(final String email) {
        if (authEmailRepository.existsByEmail(email)) {
            return;
        }
        AuthEmail authEmail = AuthEmail.builder()
            .email(email)
            .isChecked(Boolean.FALSE)
            .build();

        authEmailRepository.save(authEmail);
    }

    @Transactional
    @Override
    public void completedAuth(final String email) {
        AuthEmail authEmail = getAuthEmail(email);

        authEmail.updateChecked();
    }

    private AuthEmail getAuthEmail(String email) {
        return authEmailRepository.findByEmail(email)
            .orElseThrow(() -> new UnauthenticatedAuthEmailException(UNAUTHENTICATED_EMAIL));
    }
}
