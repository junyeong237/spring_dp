package com.example.dp.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.dp.domain.admin.service.impl.AdminMenuServiceImpl;
import com.example.dp.domain.cart.dto.request.CartRequestMenuDto;
import com.example.dp.domain.cart.dto.response.CartResponseDto;
import com.example.dp.domain.cart.repository.CartRepository;
import com.example.dp.domain.cart.service.impl.CartServiceImpl;
import com.example.dp.domain.category.entity.Category;
import com.example.dp.domain.category.repository.CategoryRepository;
import com.example.dp.domain.menu.dto.request.MenuRequestDto;
import com.example.dp.domain.menu.dto.response.MenuDetailResponseDto;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.order.dto.response.OrderResponseDto;
import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.order.repository.OrderRepository;
import com.example.dp.domain.order.service.impl.OrderServiceImpl;
import com.example.dp.domain.ordermenu.repository.OrderMenuRepository;
import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.repository.UserRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@Transactional
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
    @Autowired
    AdminMenuServiceImpl adminMenuService;
    @Autowired
    CategoryRepository categoryRepository;

    private User user;
    private Menu menu1;
    private Menu menu2;
    private Category category1;

    private Category category2;


    void setup() {
        user = User.builder()
            .username("홍길동")
            .password("123456789")
            .email("junyeong237@gmail.com")
            .role(UserRole.USER)
            .build();

        userRepository.save(user);

        category1 = Category.builder()
            .type("버거")
            .build();
        category2 = Category.builder()
            .type("치킨")
            .build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        MenuRequestDto menuRequestDto1 = MenuRequestDto.builder()
            .name("햄버거")
            .price(3000)
            .description("맛있는 햄버거")
            .price(3000)
            .quantity(50)
            .status(true)
            .categoryNameList(Arrays.asList("버거"))
            .build();
        MenuDetailResponseDto responseDto1 = adminMenuService.createMenu(menuRequestDto1);
        menu1 = menuRepository.findById(responseDto1.getId()).orElseThrow(RuntimeException::new);

        MenuRequestDto menuRequestDto2 = MenuRequestDto.builder()
            .name("치킨")
            .description("맛있는 치킨")
            .price(13000)
            .quantity(50)
            .status(true)
            .categoryNameList(Arrays.asList("치킨"))
            .build();
        MenuDetailResponseDto responseDto2 = adminMenuService.createMenu(menuRequestDto2);
        menu2 = menuRepository.findById(responseDto2.getId()).orElseThrow(RuntimeException::new);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("주문하기")
    @Disabled
    void 장바구니_생성후_주문하기() {
        setup();
        CartRequestMenuDto cartRequestMenuDto = new CartRequestMenuDto("햄버거", 2);

        CartResponseDto cart = cartService.postCart(user, cartRequestMenuDto);
        CartRequestMenuDto cart2RequestMenuDto = new CartRequestMenuDto("치킨", 2);

        CartResponseDto cart2 = cartService.postCart(user, cart2RequestMenuDto);

        OrderResponseDto orderResponseDto = orderService.createOrder(user);
        assertNotNull(orderResponseDto);
        assertEquals(Collections.emptyList(), cartRepository.findByUser(user));
        assertEquals(user.getUsername(), orderResponseDto.getUserName());
        assertEquals(2, orderResponseDto.getOrderMenuNameList().size());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("주문취소")
    @Disabled
    void 주문취소() {
        Order order = orderRepository.findById(1L).orElse(null);
        assertNotNull(order);
        orderService.cancelOrder(user, order.getId());

        List<Order> orderList = orderRepository.findByUserAndState(user, OrderState.CANCELLED);

        //취소된 주문건무
        assertEquals(1, orderList.size());
        assertEquals(OrderState.CANCELLED, orderList.get(0).getState());
    }


    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("주문조회")
    @Disabled
    void 사용자_주문_조회() {
        CartRequestMenuDto cartRequestMenuDto = new CartRequestMenuDto("햄버거", 3);

        cartService.postCart(user, cartRequestMenuDto);
        orderService.createOrder(user);

        List<Order> orderList = orderRepository.findByUser(user);

        //취소된 주문건무
        assertNotNull(orderList);
        assertEquals(2, orderList.size());
        assertEquals(OrderState.CANCELLED, orderList.get(0).getState());
        assertEquals(OrderState.PENDING, orderList.get(1).getState());
    }


}
