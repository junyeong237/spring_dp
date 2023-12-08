package com.example.dp.domain.admin.service.impl;

import com.example.dp.domain.admin.service.AdminOrderService;
import com.example.dp.domain.order.dto.request.OrderStateRequestDto;
import com.example.dp.domain.order.dto.response.OrderResponseDto;
import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.order.exception.AlreadyOrderStateCancelException;
import com.example.dp.domain.order.exception.AlreadyOrderStateFinishedException;
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
        final OrderStateRequestDto orderStateRequestDto) {
        Order order = findOrder(id);
        validateOrderState(order);
        order.updateState(orderStateRequestDto.getState());

        return new OrderResponseDto(order);
    }


    @Override
    public void cancelOrder(final Long id) {
        Order order = findOrder(id);
        validateOrderState(order);
        order.updateState(OrderState.CANCELLED);
    }

    private void validateOrderState(final Order order) {

        if (order.getState().equals(OrderState.CANCELLED)) {
            throw new AlreadyOrderStateCancelException(OrderErrorCode.ALREADY_ORDER_STATE_CANCEL);
        } else if (order.getState().equals(OrderState.COMPLETED)) {
            throw new AlreadyOrderStateFinishedException(OrderErrorCode.ALREADY_ORDER_STATE_FINISHED);
        }

    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new NotFoundOrderException(OrderErrorCode.NOT_FOUND_ORDER));
    }


}
