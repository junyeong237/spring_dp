package com.example.dp.domain.admin.service.impl;

import com.example.dp.domain.admin.service.AdminOrderService;
import com.example.dp.domain.order.dto.response.OrderResponseDto;
import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.order.exception.ForbiddenOrderState;
import com.example.dp.domain.order.exception.ForbiddenOrderStateNotCreated;
import com.example.dp.domain.order.exception.ForbiddenOrderStateNotPending;
import com.example.dp.domain.order.exception.NotFoundOrderException;
import com.example.dp.domain.order.exception.OrderErrorCode;
import com.example.dp.domain.order.repository.OrderRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersAll() {

        List<Order> orderList = orderRepository.findAll();

        if (orderList.isEmpty()) {
            return Collections.emptyList();
        }

        return orderList.stream()
            .map(OrderResponseDto::new).toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersAllToday() {
        LocalDate today = LocalDate.now();
        List<Order> orderList = orderRepository.findAllByCreatedAtAfter(today.atStartOfDay());

        if (orderList.isEmpty()) {
            return Collections.emptyList();
        }
        return orderList.stream()
            .map(OrderResponseDto::new).toList();
    }

    @Override
    public OrderResponseDto updateOrderState(final Long id,
        final OrderState state) {
        checkstate(state);
        Order order = findOrder(id);
        validateOrderState(order,state);
        order.updateState(state);
        return new OrderResponseDto(order);
    }

    @Override
    public void deleteOrder(final Long id) {

        Order order = findOrder(id);
        orderRepository.delete(order);

    }

    private void checkstate(OrderState state) {
        if(state != OrderState.COMPLETED && state != OrderState.CREATED && state != OrderState.CANCELLED){
            throw new ForbiddenOrderState(OrderErrorCode.FORBIDDEN_ORDER_STATE);
        }
    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new NotFoundOrderException(OrderErrorCode.NOT_FOUND_ORDER));
    }

    private void validateOrderState(Order order, OrderState state) {

        if(state == OrderState.CANCELLED){
            if(order.getState() != OrderState.PENDING){
                throw new ForbiddenOrderStateNotPending(OrderErrorCode.FORBIDDEN_ORDER_STATE_NOT_PENDING);
            }
        }
        else if(state == OrderState.COMPLETED){
            if(order.getState() != OrderState.CREATED){
                throw new ForbiddenOrderStateNotCreated(OrderErrorCode.FORBIDDEN_ORDER_STATE_NOT_CREATED);
            }
        }

        else if(state == OrderState.CREATED){
            if(order.getState() != OrderState.PENDING){
                throw new ForbiddenOrderStateNotPending(OrderErrorCode.FORBIDDEN_ORDER_STATE_NOT_PENDING);
            }
        }
    }

}