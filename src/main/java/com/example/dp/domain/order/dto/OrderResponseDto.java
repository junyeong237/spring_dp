package com.example.dp.domain.order.dto;

import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.ordermenu.entity.OrderMenu;
import com.example.dp.domain.user.entity.User;
import java.util.List;
import lombok.Getter;

@Getter

public class OrderResponseDto {

    private final Long id;
    private final OrderState state;
    private final User user;
    private final List<OrderMenu> orderMenuList;


    public OrderResponseDto(Order order, User user) {
        this.id = order.getId();
        this.state = order.getState();
        this.user = user;
        this.orderMenuList = order.getOrderMenuList();
    }
}
