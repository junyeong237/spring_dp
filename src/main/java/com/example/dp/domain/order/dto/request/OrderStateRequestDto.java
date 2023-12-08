package com.example.dp.domain.order.dto.request;

import com.example.dp.domain.order.entity.OrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class OrderStateRequestDto {

    private OrderState state;

}
