package com.example.dp.domain.user.password.repository;

import com.example.dp.domain.user.password.entity.PasswordHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

    List<PasswordHistory> findByUser_Id(Long UserId);

    PasswordHistory findTop1ByUser_IdOrderByCreatedAt(Long userId);

}
