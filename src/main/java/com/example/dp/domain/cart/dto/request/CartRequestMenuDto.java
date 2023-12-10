package com.example.dp.domain.cart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CartRequestMenuDto {


    private String name; // 메뉴 이름

    private Integer menuCounts; //해당 메뉴의 갯수

}
