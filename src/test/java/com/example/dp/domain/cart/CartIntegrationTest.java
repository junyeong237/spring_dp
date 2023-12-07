package com.example.dp.domain.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.dp.domain.admin.service.impl.AdminMenuServiceImpl;
import com.example.dp.domain.cart.dto.request.CartDeleteRequestMenuDto;
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
import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
public class CartIntegrationTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    CartServiceImpl cartService;
    @Autowired
    AdminMenuServiceImpl adminMenuService;
    @Autowired
    CategoryRepository categoryRepository;

    private User user;
    private Menu menu1;
    private Menu menu2;

    private Category category1;

    private Category category2;

    @BeforeAll
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
    @Order(1)
    @DisplayName("장바구니 생성")
    void 주문자_장바구니_생성() {

        CartRequestMenuDto cartRequestMenuDto = new CartRequestMenuDto("햄버거", 2, 6000);

        CartResponseDto cart = cartService.postCart(user, cartRequestMenuDto);
        assertNotNull(cart);

        assertEquals("햄버거", cart.getMenu().getName());
        assertEquals(2, cart.getMenuCounts());


    }

    @Test
    @Order(2)
    @DisplayName("장바구니에 수량 추가 ")
    void 주문자_장바구니_수량추가() {

        CartRequestMenuDto cartRequestMenuDto2 = new CartRequestMenuDto("햄버거", 2, 6000);

        CartResponseDto cart = cartService.postCart(user, cartRequestMenuDto2);
        assertNotNull(cart);

        assertEquals("햄버거", cart.getMenu().getName());
        assertEquals(4, cart.getMenuCounts());

    }

    @Test
    @Order(3)
    @DisplayName("장바구니 새로 추가 ")
    void 주문자_장바구니_추가() {

        CartRequestMenuDto cartRequestMenuDto = new CartRequestMenuDto("치킨", 2, 26000);

        CartResponseDto cart = cartService.postCart(user, cartRequestMenuDto);
        assertNotNull(cart);

        assertEquals("치킨", cart.getMenu().getName());
        assertEquals(2, cart.getMenuCounts());
    }

    @Test
    @Order(4)
    @DisplayName("장바구니 메뉴삭제 ")
    void 주문자_장바구니_특정메뉴삭제() {

        CartDeleteRequestMenuDto cartRequestMenuDto = new CartDeleteRequestMenuDto("햄버거");
        cartService.deleteCartMenu(user, cartRequestMenuDto);
        List<CartResponseDto> cart = cartService.getCart(user);

        assertEquals(1, cart.size());
    }

    @Test
    @Order(5)
    @DisplayName("사용자 장바구니 전체 삭제 ")
    void 주문자_장바구니_전체삭제() {

        cartService.deleteCart(user);
        List<CartResponseDto> cart = cartService.getCart(user);

        assertEquals(0, cart.size());
    }
}









