package com.example.dp.domain.menu.dto.response;

import com.example.dp.domain.menu.entity.Menu;
import java.util.List;
import lombok.Getter;

@Getter
public class MenuDetailResponseDto {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer price;
    private final Integer quantity;
    private final Boolean status;
    private final List<String> categoryNameList;

    public MenuDetailResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.description = menu.getDescription();
        this.price = menu.getPrice();
        this.quantity = menu.getQuantity();
        this.status = menu.getStatus();
        this.categoryNameList = menu.getMenuCategoryList().stream()
            .map(menuCategory -> menuCategory.getMenu().getName())
            .toList();
    }
}
