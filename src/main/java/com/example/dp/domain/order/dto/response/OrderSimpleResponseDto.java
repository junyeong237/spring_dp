package com.example.dp.domain.order.dto.response;

import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.ordermenu.entity.OrderMenu;
import com.example.dp.domain.user.entity.User;
import java.util.List;
import lombok.Getter;

@Getter
public class OrderSimpleResponseDto {

    private final Long id;
    private final OrderState state;
    private final User user;

    public OrderSimpleResponseDto(Order order) {
        this.id = order.getId();
        this.state = order.getState();
        this.user = order.getUser();
    }

}
