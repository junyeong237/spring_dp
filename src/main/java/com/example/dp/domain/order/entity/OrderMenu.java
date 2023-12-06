package com.example.dp.domain.order.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;

@Entity
@Getter
@Table(name = "TB_ORDER_MENU")
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Builder
    private OrderMenu(Order order, Menu menu){
        this.order =  order;
        this.menu = menu;
    }
}
