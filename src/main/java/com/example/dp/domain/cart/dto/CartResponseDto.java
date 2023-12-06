package com.example.dp.domain.cart.dto;


import com.example.dp.domain.cart.entity.Cart;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CartResponseDto {

    private Long id;
    private Long userId;
    private Long menuId;
    private Long menuCounts;

    private LocalDateTime createdAt;

    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.menuId = cart.getMenu().getId();
        this.menuCounts = cart.getMenuCount();
    }


}
