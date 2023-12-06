package com.example.dp.domain.order.entity;


import com.example.dp.domain.model.TimeEntity;
import com.example.dp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@Getter
@Entity
@Table(name="TB_ORDER")
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

    @Builder
    private Order(User user, OrderStateEnum state){
        this.user = user;
        this.state = state;
    }


}
