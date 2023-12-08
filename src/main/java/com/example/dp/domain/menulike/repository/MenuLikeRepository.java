package com.example.dp.domain.menulike.repository;

import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menulike.entity.MenuLike;
import com.example.dp.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuLikeRepository extends JpaRepository<MenuLike, Long> {

    List<MenuLike> findByUserId(Long userId);

    Boolean existsByUserAndMenu(User user, Menu menu);

    Optional<MenuLike> findByUserAndMenu(User user, Menu menu);

    List<MenuLike> findAllByMenu(Menu menu);
}
