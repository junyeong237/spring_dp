package com.example.dp.domain.menu.repository;

import com.example.dp.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByName(String name);
}
