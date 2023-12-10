package com.example.dp.domain.user.service;

import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.dto.response.PasswordHistoryResponseDto;
import java.util.List;

public interface PasswordHistoryService {

    List<PasswordHistoryResponseDto> getPasswordHistory(Long userId);

    void addPasswordHistory(String password, User user);

    void deletePasswordHistory(Long userId);
}
