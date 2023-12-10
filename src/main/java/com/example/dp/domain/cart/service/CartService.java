package com.example.dp.domain.cart.service;

import com.example.dp.domain.cart.dto.request.CartDeleteRequestMenuDto;
import com.example.dp.domain.cart.dto.request.CartRequestMenuDto;
import com.example.dp.domain.cart.dto.response.CartResponseDto;
import com.example.dp.domain.user.entity.User;
import java.util.List;

public interface CartService {

    List<CartResponseDto> getCart(User user);

    CartResponseDto postCart(User user, CartRequestMenuDto cartRequestMenuDto);

    void deleteCartMenu(User user, Long id);

    void deleteCart(User user);

}
