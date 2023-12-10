package com.example.dp.domain.admin.service;

import com.example.dp.domain.order.dto.response.OrderResponseDto;
import com.example.dp.domain.order.entity.OrderState;
import java.util.List;

public interface AdminOrderService {

    List<OrderResponseDto> getOrdersAll();

    List<OrderResponseDto> getOrdersAllToday();

    OrderResponseDto updateOrderState(Long id, OrderState state);

    void deleteOrder(Long id);

}
