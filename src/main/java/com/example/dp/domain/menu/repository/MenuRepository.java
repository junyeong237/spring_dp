package com.example.dp.domain.menu.repository;

import com.example.dp.domain.menu.entity.Menu;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByName(String name);

    List<Menu> findByOrderByCreatedAt();
}
