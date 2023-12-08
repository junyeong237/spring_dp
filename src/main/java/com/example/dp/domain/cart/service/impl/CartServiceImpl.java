package com.example.dp.domain.cart.service.impl;

import com.example.dp.domain.cart.dto.request.CartDeleteRequestMenuDto;
import com.example.dp.domain.cart.dto.request.CartRequestMenuDto;
import com.example.dp.domain.cart.dto.response.CartResponseDto;
import com.example.dp.domain.cart.entity.Cart;
import com.example.dp.domain.cart.exception.CartErrorCode;
import com.example.dp.domain.cart.exception.NotFoundCartMenuExcepiton;
import com.example.dp.domain.cart.exception.NotFoundMenuException;
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
        List<Cart> cartList = cartRepository.findByUser(user);

        Optional<Cart> cartMenu = cartRepository.findByUserAndMenu(user, menu);
        if (cartMenu.isEmpty()) { // 사용자가 해당메뉴에 대한 장바구니가 없다면
            Cart newCart = Cart.builder()
                .user(user)
                .menu(menu)
                .menuCount(cartRequestMenuDto.getMenuCounts())
                .build();

            cartList.add(newCart);
            cartRepository.saveAll(cartList);

            return new CartResponseDto(newCart);
        }

        Cart newCart = cartMenu.get();
        //사용자가 해당메뉴에 대한 장바구니를 가지고 있다면 수량만추가
        newCart.addCount(cartRequestMenuDto.getMenuCounts());

        return new CartResponseDto(newCart);
    }

    @Override
    public void deleteCartMenu(final User user, final Long menuId) {
        Menu menu = findMenuById(menuId);
        Cart cart = cartRepository.findByUserAndMenu(user, menu)
            .orElseThrow(() -> new NotFoundCartMenuExcepiton(CartErrorCode.NOT_FOUND_CART_MENU));

        List<Cart> cartList = cartRepository.findByUser(user);
        cartList.remove(cart);
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
            .orElseThrow(() -> new NotFoundMenuException(CartErrorCode.NOT_FOUND_MENU));
    }

    private Menu findMenuById(Long id){
        return menuRepository.findById(id)
            .orElseThrow(() -> new NotFoundMenuException(CartErrorCode.NOT_FOUND_MENU));
    }
}
