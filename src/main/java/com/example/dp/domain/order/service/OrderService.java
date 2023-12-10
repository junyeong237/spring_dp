package com.example.dp.domain.order.service;


import com.example.dp.domain.order.dto.response.OrderResponseDto;
import com.example.dp.domain.user.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    OrderResponseDto createOrder(User user);

    void cancelOrder(User user, Long id);

    List<OrderResponseDto> getOrder(User user);
}
