package com.example.dp.domain.cart.dto.response;


import com.example.dp.domain.cart.entity.Cart;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CartResponseDto {

    private final Long id;
    private final String userName;
    private final String menuName;
    private final Integer menuCounts;
    private final LocalDateTime createdAt;
    private final Integer totalPrice;


    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.userName = cart.getUser().getUsername();
        this.menuName = cart.getMenu().getName();
        this.menuCounts = cart.getMenuCount();
        this.createdAt = cart.getCreatedAt();
        this.totalPrice = cart.getTotalPrice();
    }


}
