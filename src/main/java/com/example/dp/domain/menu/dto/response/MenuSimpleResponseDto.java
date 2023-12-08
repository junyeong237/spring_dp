package com.example.dp.domain.menu.dto.response;

import com.example.dp.domain.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuSimpleResponseDto {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer price;
    private final Integer likeCounts;

    public MenuSimpleResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.description = menu.getDescription();
        this.price = menu.getPrice();
        this.likeCounts = menu.getMenuLikeList().size();
    }
}
