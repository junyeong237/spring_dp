package com.example.dp.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.dp.domain.cart.dto.request.CartRequestMenuDto;
import com.example.dp.domain.cart.dto.response.CartResponseDto;
import com.example.dp.domain.cart.repository.CartRepository;
import com.example.dp.domain.cart.service.impl.CartServiceImpl;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.order.dto.OrderResponseDto;
import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.order.repository.OrderRepository;
import com.example.dp.domain.order.service.impl.OrderServiceImpl;
import com.example.dp.domain.ordermenu.repository.OrderMenuRepository;
import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
public class OrderIntegrationTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderMenuRepository orderMenuRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    CartServiceImpl cartService;

    @Autowired
    OrderServiceImpl orderService;

    private User user;
    private Menu menu1;
    private Menu menu2;

    @BeforeAll
    void setup() {
        user = User.builder()
            .username("홍길동")
            .password("123456789")
            .email("junyeong237@gmail.com")
            .role(UserRole.USER)
            .build();

        userRepository.save(user);
        menu1 = Menu.builder()
            .name("햄버거")
            .price(3000)
            .description("맛있는 햄버거")
            .quantity(50)
            .build();
        menu2 = Menu.builder()
            .name("치킨")
            .price(13000)
            .description("맛있는 치킨")
            .quantity(50)
            .build();
        menuRepository.save(menu1);
        menuRepository.save(menu2);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("주문하기")
    void 장바구니_생성후_주문하기() {

        CartRequestMenuDto cartRequestMenuDto = new CartRequestMenuDto("햄버거", 2, 6000);

        CartResponseDto cart = cartService.postCart(user, cartRequestMenuDto);
        CartRequestMenuDto cart2RequestMenuDto = new CartRequestMenuDto("치킨", 2, 26000);

        CartResponseDto cart2 = cartService.postCart(user, cart2RequestMenuDto);

        OrderResponseDto orderResponseDto = orderService.createOrder(user);
        assertNotNull(orderResponseDto);
        assertEquals(Collections.emptyList(),cartRepository.findByUser(user));
        assertEquals(user.getUsername(), orderResponseDto.getUser().getUsername());
        assertEquals(2, orderResponseDto.getOrderMenuList().size());

    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("주문취소")
    void 주문취소() {

        Order order = orderRepository.findById(1L)
            .orElseThrow(()-> new IllegalArgumentException("해당하는 주문이 없습니다."));
        orderService.deleteOrder(user,order.getId());

        List<Order> orderList = orderRepository.findByUserAndState(user,OrderState.CANCELLED);

        //취소된 주문건무
        assertEquals(1,orderList.size());
        assertEquals(OrderState.CANCELLED, orderList.get(0).getState());


    }


    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("주문조회")
    void 사용자_주문_조회() {
        CartRequestMenuDto cartRequestMenuDto = new CartRequestMenuDto("햄버거", 3, 9000);

        CartResponseDto cart = cartService.postCart(user, cartRequestMenuDto);
        OrderResponseDto orderResponseDto = orderService.createOrder(user);

        List<Order> orderList = orderRepository.findByUser(user);

        //취소된 주문건무
        assertNotNull(orderList);
        assertEquals(2,orderList.size());
        assertEquals(OrderState.CANCELLED, orderList.get(0).getState());
        assertEquals(OrderState.PENDING, orderList.get(1).getState());


    }


}
