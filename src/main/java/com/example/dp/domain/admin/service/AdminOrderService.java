package com.example.dp.domain.admin.service;

import com.example.dp.domain.order.dto.request.OrderStateRequestDto;
import com.example.dp.domain.order.dto.response.OrderResponseDto;
import java.util.List;

public interface AdminOrderService {

    List<OrderResponseDto> getOrdersAll();

    List<OrderResponseDto> getOrdersAllToday();

    OrderResponseDto updateOrderState(Long id, OrderStateRequestDto orderStateRequestDto);

    void cancelOrder(Long id);

}
