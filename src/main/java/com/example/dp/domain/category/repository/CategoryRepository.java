package com.example.dp.domain.category.repository;

import com.example.dp.domain.category.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByType(String categoryType);

    List<Category> findByTypeIn(List<String> list);

    boolean existsByType(String type);
}
