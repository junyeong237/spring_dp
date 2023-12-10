package com.example.dp.domain.user.service.impl;

import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.dto.response.PasswordHistoryResponseDto;
import com.example.dp.domain.user.entity.PasswordHistory;
import com.example.dp.domain.user.repository.PasswordHistoryRepository;
import com.example.dp.domain.user.service.PasswordHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordHistoryServiceImpl implements PasswordHistoryService {

    private final PasswordHistoryRepository passwordHistoryRepository;

    @Override
    public List<PasswordHistoryResponseDto> getPasswordHistory(final Long userId) {
        return passwordHistoryRepository.findByUser_Id(userId).stream()
            .map((PasswordHistory p) -> PasswordHistoryResponseDto.of(p.getPassword()))
            .toList();
    }

    @Override
    public void addPasswordHistory(final String password, final User user) {
        PasswordHistory passwordHistory = PasswordHistory.builder()
            .password(password)
            .user(user)
            .build();

        passwordHistoryRepository.save(passwordHistory);
    }

    @Override
    public void deletePasswordHistory(final Long userId) {
        PasswordHistory passwordHistory = passwordHistoryRepository.findTop1ByUser_IdOrderByCreatedAt(
            userId);

        passwordHistoryRepository.delete(passwordHistory);
    }
}
