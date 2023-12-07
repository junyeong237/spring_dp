package com.example.dp.domain.order.dto.response;

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
    private final String userName;
    private final List<String> orderMenuNameList;


    public OrderResponseDto(Order order, User user) {
        this.id = order.getId();
        this.state = order.getState();
        this.userName = user.getUsername();
        this.orderMenuNameList = order.getOrderMenuList()
            .stream().map(orderMenu -> {
                return orderMenu.getMenu().getName();
            })
            .toList();
    }
}
