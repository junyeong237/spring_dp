package com.example.dp.domain.menulike.entity;

import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.model.TimeEntity;
import com.example.dp.domain.user.entity.User;
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

@Entity
@Table(name = "TB_LIKE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuLike extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private MenuLike(User user, Menu menu) {
        this.user = user;
        this.menu = menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

}
