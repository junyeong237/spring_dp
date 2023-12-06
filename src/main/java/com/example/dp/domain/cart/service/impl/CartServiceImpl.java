package com.example.dp.domain.cart.service.impl;

import com.example.dp.domain.cart.dto.CartResponseDto;
import com.example.dp.domain.cart.entity.Cart;
import com.example.dp.domain.cart.repository.CartRepository;
import com.example.dp.domain.cart.service.CartService;
import com.example.dp.domain.user.entity.User;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public List<CartResponseDto> getCart(User user) {

        List<Cart> cartList = cartRepository.findByUser(user);
        if (cartList.isEmpty()) {
            return Collections.emptyList();
        }

        return cartList.stream()
            .map(CartResponseDto::new)
            .toList();
    }


}
