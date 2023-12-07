package com.example.dp.domain.menu.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuRequestDto {

    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
    private Boolean status;
    private List<String> categoryNameList;
}
