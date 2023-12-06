package com.example.dp.domain.cart.service;

import com.example.dp.domain.cart.dto.CartResponseDto;
import com.example.dp.domain.user.entity.User;
import java.util.List;

public interface CartService {

    List<CartResponseDto> getCart(User user);

}
