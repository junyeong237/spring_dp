package com.example.dp.domain.menucategory.repository;

import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menucategory.entity.MenuCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    List<MenuCategory> findByMenuId(Long menuId);

    boolean existsByCategoryId(Long categoryId);

    List<MenuCategory> findByCategoryId(Long categoryId);

    List<MenuCategory> findByCategory_TypeIn(List<String> categoryNameList);

    List<MenuCategory> findByMenuAndCategory_TypeIn(Menu menu, List<String> categoryNameList);
}
