package com.example.dp.domain.menu.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuRequestDto {

    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
}
