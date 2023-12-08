package com.example.dp.domain.order.dto.response;

import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.ordermenu.entity.OrderMenu;
import com.example.dp.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class OrderResponseDto {

    private final Long id;
    private final OrderState state;
    private final String userName;
    private final List<String> orderMenuNameList;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.state = order.getState();
        this.userName = order.getUser().getUsername();
        this.createdAt = order.getCreatedAt();
        this.modifiedAt = order.getModifiedAt();
        this.orderMenuNameList = order.getOrderMenuList()
            .stream().map(orderMenu -> {
                return orderMenu.getMenu().getName();
            })
            .toList();
    }
}
