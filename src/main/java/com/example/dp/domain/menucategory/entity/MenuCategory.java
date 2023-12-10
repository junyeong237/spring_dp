package com.example.dp.domain.menucategory.entity;

import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.menu.entity.Menu;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Table(name = "TB_MENU_CATEGORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;

    @Builder
    private MenuCategory(Category category, Menu menu) {
        this.category = category;
        this.menu = menu;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
