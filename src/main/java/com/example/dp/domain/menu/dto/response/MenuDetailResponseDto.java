package com.example.dp.domain.menu.dto.response;

import com.example.dp.domain.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuDetailResponseDto {

    private final String name;
    private final String description;
    private final Integer price;
    private final Integer quantity;
    private final Boolean status;

    public MenuDetailResponseDto(Menu menu) {
        this.name = menu.getName();
        this.description = menu.getDescription();
        this.price = menu.getPrice();
        this.quantity = menu.getQuantity();
        this.status = menu.getStatus();
    }
}
