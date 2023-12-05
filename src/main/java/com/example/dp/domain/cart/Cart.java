package com.example.dp.domain.cart;


import com.example.dp.domain.model.TimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@Getter
@Entity
@Table(name="cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private Long menuCount;

    @Builder
    private Cart(User user, Menu menu) {
        this.user = user;
        this.menu = menu;
    }







}
