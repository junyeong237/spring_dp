package com.example.dp.domain.cart.dto.response;


import com.example.dp.domain.cart.entity.Cart;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartResponseDto {

    private final Long id;
    private final Long userId;
    private final Long menuId;
    private final Integer menuCounts;
    private final LocalDateTime createdAt;

    @Builder
    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.menuId = cart.getMenu().getId();
        this.menuCounts = cart.getMenuCount();
        this.createdAt = cart.getCreatedAt();
    }


}
