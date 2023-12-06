package com.example.dp.domain.order.entity;

import com.example.dp.domain.model.TimeEntity;
import com.example.dp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="TB_ORDER")

@Entity
@Table(name = "TB_ORDER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)

    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStateEnum state;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenuList = new ArrayList<>();

    @Builder
    private Order(User user, OrderStateEnum state){
        this.user = user;
        this.state = state;
    }

}
