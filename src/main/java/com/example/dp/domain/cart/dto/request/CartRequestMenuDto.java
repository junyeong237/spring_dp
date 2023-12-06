package com.example.dp.domain.cart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CartRequestMenuDto {


    private String name; // 메뉴 이름

    private Integer menuCounts; //해당 메뉴의 갯수

    private Integer priceSum; //총 가격


}
