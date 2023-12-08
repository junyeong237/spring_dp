package com.example.dp.domain.menulike.repository;

import com.example.dp.domain.menulike.entity.MenuLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuLikeRepository extends JpaRepository<MenuLike, Long> {

    List<MenuLike> findByUserId(Long userId);
}
