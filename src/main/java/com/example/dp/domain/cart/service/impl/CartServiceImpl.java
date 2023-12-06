package com.example.dp.domain.cart.service.impl;

import com.example.dp.domain.cart.dto.request.CartRequestMenuDto;
import com.example.dp.domain.cart.dto.response.CartResponseDto;
import com.example.dp.domain.cart.entity.Cart;
import com.example.dp.domain.cart.repository.CartRepository;
import com.example.dp.domain.cart.service.CartService;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.user.entity.User;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CartResponseDto> getCart(User user) {

        List<Cart> cartList = cartRepository.findByUser(user);
        if (cartList.isEmpty()) {
            return Collections.emptyList();
        }

        return cartList.stream()
            .map(CartResponseDto::new)
            .toList();
    }

    @Override
    public CartResponseDto postCart(User user, CartRequestMenuDto cartRequestMenuDto) {
        Menu menu = findMenuByname(cartRequestMenuDto.getName());

        Optional<Cart> cart = cartRepository.findByUserAndMenu(user, menu);
        if (cart.isEmpty()) { // 사용자가 해당하는 메뉴에대한 장바구니를 가지고 있지 않다면 새로 장바구니 생성
            Cart newCart = Cart.builder()
                .menu(menu)
                .user(user)
                .menuCount(cartRequestMenuDto.getMenuCounts())
                .build();
            cartRepository.save(newCart);
            return new CartResponseDto(newCart);
        }
        Cart newCart = cart.get();
        //사용자가 해당메뉴에 대한 장바구니를 가지고 있다면 수량만추가
        newCart.addCount(cartRequestMenuDto.getMenuCounts());

        return new CartResponseDto(newCart);
    }

    @Override
    public void deleteCartMenu(final User user, final CartRequestMenuDto cartRequestMenuDto) {
        Menu menu = findMenuByname(cartRequestMenuDto.getName());

        Cart cart = cartRepository.findByUserAndMenu(user, menu)
            .orElseThrow(() -> new IllegalArgumentException("삭제하려는 메뉴에대한 장바구니 리스트가 없습니다."));

        cartRepository.delete(cart);

    }

    @Override
    public void deleteCart(final User user) {
        List<Cart> cartList = cartRepository.findByUser(user);
        if (cartList.isEmpty()) {
            return;
        }
        cartRepository.deleteAll(cartList);
    }

    private Menu findMenuByname(String menuName) {
        return menuRepository.findByName(menuName)
            .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));
    }
}
