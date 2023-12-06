package com.example.dp.domain.cart.entity;

import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.model.TimeEntity;
import com.example.dp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TB_CART")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private Long menuCount;

    @Builder
    private Cart(User user, Menu menu, Long menuCount) {
        this.user = user;
        this.menu = menu;
        this.menuCount = menuCount;
    }


}
