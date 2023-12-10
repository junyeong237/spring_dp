package com.example.dp.domain.user.repository;

import com.example.dp.domain.user.entity.AuthEmail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthEmailRepository extends JpaRepository<AuthEmail, Long> {

    Optional<AuthEmail> findByEmail(String email);
    Boolean existsByEmail(String email);
}
