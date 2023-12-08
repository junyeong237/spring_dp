package com.example.dp.domain.order.service.impl;

import com.example.dp.domain.cart.entity.Cart;
import com.example.dp.domain.cart.repository.CartRepository;
import com.example.dp.domain.cart.service.impl.CartServiceImpl;
import com.example.dp.domain.order.dto.response.OrderResponseDto;
import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.order.exception.ForbiddenDeleteOrderRoleExcepiton;
import com.example.dp.domain.order.exception.ForbiddenOrderQuantity;
import com.example.dp.domain.order.exception.ForbiddenOrderStateNotPending;
import com.example.dp.domain.order.exception.NotFoundCartListForOrderException;
import com.example.dp.domain.order.exception.NotFoundOrderException;
import com.example.dp.domain.order.exception.OrderErrorCode;
import com.example.dp.domain.order.repository.OrderRepository;
import com.example.dp.domain.order.service.OrderService;
import com.example.dp.domain.ordermenu.entity.OrderMenu;
import com.example.dp.domain.ordermenu.repository.OrderMenuRepository;
import com.example.dp.domain.user.UserRole;
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
            throw new NotFoundCartListForOrderException(
                OrderErrorCode.NOT_FOUND_CARTLIST_FOR_ORDER);
        }
        Order order = Order.builder()
            .user(user)
            .state(OrderState.PENDING)
            .build();


        List<OrderMenu> orderMenuList = cartList.stream()
            .map(cart -> {
                    OrderMenu orderMenu = new OrderMenu(order, cart.getMenu(),cart.getMenuCount(),cart.getTotalPrice());
                    order.addOrderMenuList(orderMenu);
                    cart.getMenu().addOrderMenu(orderMenu);
                    return orderMenu;
                }
            )
            .toList();

        //수량 체크
        for(OrderMenu orderMenu : orderMenuList){
            if(orderMenu.getMenuCounts() > orderMenu.getMenu().getQuantity()){
                throw new ForbiddenOrderQuantity(OrderErrorCode.FORBIDDEN_ORDER_QUANTITY);
            }
            orderMenu.getMenu().subQuantity(orderMenu.getMenuCounts());
        }

        orderRepository.save(order);
        orderMenuRepository.saveAll(orderMenuList);

        //장바구니 내역 삭제
        cartService.deleteCart(user);

        return new OrderResponseDto(order);


    }

    @Override
    @Transactional
    public void cancelOrder(final User user, final Long orderId) {

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundOrderException(OrderErrorCode.NOT_FOUND_ORDER));


        if(!order.getUser().getId().equals(user.getId())){ // 주문을 한 사용자가 아니고
            if(!user.getRole().equals(UserRole.ADMIN)){ //사용자가 관리자가 아닌경우
                throw new ForbiddenDeleteOrderRoleExcepiton(OrderErrorCode.FORBIDDEN_DELETE_ORDER_ROLE);
            }
        }

        if (order.getState().equals(OrderState.PENDING)) {
            order.updateState(OrderState.CANCELLED);
        }

        else{
            throw new ForbiddenOrderStateNotPending(OrderErrorCode.FORBIDDEN_ORDER_STATE_NOT_PENDING);
        }
    }

    @Override
    public List<OrderResponseDto> getOrder(final User user) {
        List<Order> orderList = orderRepository.findByUser(user);

        return orderList.stream()
            .map(OrderResponseDto::new)
            .toList();


    }


}
