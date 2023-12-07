package com.example.dp.domain.cart.dto.response;


import com.example.dp.domain.cart.entity.Cart;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CartResponseDto {

    private final Long id;
    private final User user;
    private final Menu menu;
    private final Integer menuCounts;
    private final LocalDateTime createdAt;


    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.user = cart.getUser();
        this.menu = cart.getMenu();
        this.menuCounts = cart.getMenuCount();
        this.createdAt = cart.getCreatedAt();
    }


}
