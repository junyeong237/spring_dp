package com.example.dp.domain.order.service.impl;

import com.example.dp.domain.cart.entity.Cart;
import com.example.dp.domain.cart.repository.CartRepository;
import com.example.dp.domain.cart.service.impl.CartServiceImpl;
import com.example.dp.domain.order.dto.OrderResponseDto;
import com.example.dp.domain.order.dto.response.OrderSimpleResponseDto;
import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.order.repository.OrderRepository;
import com.example.dp.domain.order.service.OrderService;
import com.example.dp.domain.ordermenu.entity.OrderMenu;
import com.example.dp.domain.ordermenu.repository.OrderMenuRepository;
import com.example.dp.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final CartServiceImpl cartService;

    @Override
    @Transactional
    public OrderResponseDto createOrder(final User user) {
        List<Cart> cartList = cartRepository.findByUser(user);

        if (cartList.isEmpty()) {
            throw new IllegalArgumentException("주문하실 장바구니가 없습니다.");
        }
        Order order = Order.builder()
            .user(user)
            .state(OrderState.PENDING)
            .build();

        orderRepository.save(order);

        List<OrderMenu> orderMenuList = cartList.stream()
            .map(cart -> {
                OrderMenu orderMenu = new OrderMenu(order, cart.getMenu());
                order.addOrderMenuList(orderMenu);
                return orderMenu;
            }
            )
            .toList();

        orderMenuRepository.saveAll(orderMenuList);


        //장바구니 내역 삭제
        cartService.deleteCart(user);

        return new OrderResponseDto(order, user);


    }

    @Override
    @Transactional
    public void deleteOrder(final User user, final Long orderId) {

        Order order = orderRepository.findById(orderId)
            .orElseThrow(()->new IllegalArgumentException("해당하는 주문이 없습니다."));

        if(!order.getUser().getId().equals(user.getId())){
            //주문을 한 사용자와 현재 사용자가 불일치할경우
            throw new RuntimeException("주문자만 삭제할 수 있습니다.");
        }

        if(order.getState().equals(OrderState.PENDING)){
            order.updateState(OrderState.CANCELLED);
        }
    }

    @Override
    public List<OrderSimpleResponseDto> getOrder(final User user) {
        List<Order> orderList = orderRepository.findByUser(user);

        return orderList.stream()
            .map(OrderSimpleResponseDto::new)
            .toList();


    }


}
