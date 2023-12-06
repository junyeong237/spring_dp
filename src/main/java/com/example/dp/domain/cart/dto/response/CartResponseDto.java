package com.example.dp.domain.cart.dto.response;


import com.example.dp.domain.cart.entity.Cart;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CartResponseDto {

    private Long id;
    private Long userId;
    private Long menuId;
    private Integer menuCounts;
    private LocalDateTime createdAt;

    @Builder
    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.menuId = cart.getMenu().getId();
        this.menuCounts = cart.getMenuCount();
        this.createdAt = cart.getCreatedAt();
    }


}