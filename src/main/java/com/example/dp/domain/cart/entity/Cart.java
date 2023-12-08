package com.example.dp.domain.cart.entity;

import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.model.TimeEntity;
import com.example.dp.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private Integer menuCount;

    @Column(nullable = false)
    private Integer totalPrice;

    @Builder
    private Cart(User user, Menu menu, Integer menuCount,Integer totalPrice) {
        this.user = user;
        this.menu = menu;
        this.menuCount = menuCount;
        this.totalPrice = totalPrice;
    }

    public void addCount(Integer count) {
        this.menuCount += count;
    }


}
