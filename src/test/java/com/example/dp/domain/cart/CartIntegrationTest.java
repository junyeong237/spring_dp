package com.example.dp.domain.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
public class CartIntegrationTest {

//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    CartRepository cartRepository;
//    @Autowired
//    MenuRepository menuRepository;
//    @Autowired
//    CartServiceImpl cartService;
//    @Autowired
//    AdminMenuServiceImpl adminMenuService;
//    @Autowired
//    CategoryRepository categoryRepository;
//
//    private User user;
//    private Menu menu1;
//    private Menu menu2;
//
//    private Category category1;
//
//    private Category category2;
//
//    void setup() {
//        user = User.builder()
//            .username("홍길동")
//            .password("123456789")
//            .email("junyeong237@gmail.com")
//            .role(UserRole.USER)
//            .build();
//
//        userRepository.save(user);
//
//        category1 = Category.builder()
//            .type("버거")
//            .build();
//        category2 = Category.builder()
//            .type("치킨")
//            .build();
//
//        categoryRepository.save(category1);
//        categoryRepository.save(category2);
//
//        MenuRequestDto menuRequestDto1 = MenuRequestDto.builder()
//            .name("햄버거")
//            .price(3000)
//            .description("맛있는 햄버거")
//            .price(3000)
//            .quantity(50)
//            .status(true)
//            .categoryNameList(Arrays.asList("버거"))
//            .build();
//        MenuDetailResponseDto responseDto1 = adminMenuService.createMenu(
//            menuRequestDto1);
//        menu1 = menuRepository.findById(responseDto1.getId()).orElseThrow(RuntimeException::new);
//
//        MenuRequestDto menuRequestDto2 = MenuRequestDto.builder()
//            .name("치킨")
//            .description("맛있는 치킨")
//            .price(13000)
//            .quantity(50)
//            .status(true)
//            .categoryNameList(Arrays.asList("치킨"))
//            .build();
//        MenuDetailResponseDto responseDto2 = adminMenuService.createMenu(
//            menuRequestDto2);
//        menu2 = menuRepository.findById(responseDto2.getId()).orElseThrow(RuntimeException::new);
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("장바구니 생성")
//    @Disabled
//    void 주문자_장바구니_생성() {
//        setup();
//        CartRequestMenuDto cartRequestMenuDto = new CartRequestMenuDto("햄버거", 2);
//
//        CartResponseDto cart = cartService.postCart(user, cartRequestMenuDto);
//        assertNotNull(cart);
//
//        assertEquals("햄버거", cart.getMenuName());
//        assertEquals(2, cart.getMenuCounts());
//
//
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("장바구니에 수량 추가 ")
//    @Disabled
//    void 주문자_장바구니_수량추가() {
//
//        CartRequestMenuDto cartRequestMenuDto2 = new CartRequestMenuDto("햄버거", 2);
//
//        CartResponseDto cart = cartService.postCart(user, cartRequestMenuDto2);
//        assertNotNull(cart);
//
//        assertEquals("햄버거", cart.getMenuName());
//        assertEquals(4, cart.getMenuCounts());
//
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("장바구니 새로 추가 ")
//    @Disabled
//    void 주문자_장바구니_추가() {
//
//        CartRequestMenuDto cartRequestMenuDto = new CartRequestMenuDto("치킨", 2);
//
//        CartResponseDto cart = cartService.postCart(user, cartRequestMenuDto);
//        assertNotNull(cart);
//
//        assertEquals("치킨", cart.getMenuName());
//        assertEquals(2, cart.getMenuCounts());
//    }
//
//    @Test
//    @Order(4)
//    @DisplayName("장바구니 메뉴삭제 ")
//    @Disabled
//    void 주문자_장바구니_특정메뉴삭제() {
//
//        Long deleteMenuId = menu1.getId();
//        cartService.deleteCartMenu(user, deleteMenuId);
//        List<CartResponseDto> cart = cartService.getCart(user);
//
//        assertEquals(1, cart.size());
//    }
//
//    @Test
//    @Order(5)
//    @DisplayName("사용자 장바구니 전체 삭제 ")
//    @Disabled
//    void 주문자_장바구니_전체삭제() {
//
//        cartService.deleteCart(user);
//        List<CartResponseDto> cart = cartService.getCart(user);
//
//        assertEquals(0, cart.size());
//    }
}









