package com.example.dp.domain.order.service;


import com.example.dp.domain.order.dto.OrderResponseDto;
import com.example.dp.domain.order.dto.request.OrderRequestDto;
import com.example.dp.domain.order.dto.response.OrderSimpleResponseDto;
import com.example.dp.domain.user.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    OrderResponseDto createOrder(User user);

    void deleteOrder(User user,Long id);

    List<OrderSimpleResponseDto> getOrder(User user);
}
