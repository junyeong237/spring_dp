package com.example.dp.domain.admin.controller;

import com.example.dp.domain.admin.service.impl.AdminOrderServiceImpl;
import com.example.dp.domain.order.dto.request.OrderStateRequestDto;
import com.example.dp.domain.order.dto.response.OrderResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderServiceImpl adminOrderService;

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDto>> getOrdersAll() {
        List<OrderResponseDto> orderListAll = adminOrderService.getOrdersAll();
        return ResponseEntity.status(HttpStatus.OK).body(orderListAll);
    }

    @GetMapping("/today")
    public ResponseEntity<List<OrderResponseDto>> getOrdersAllToday() {
        List<OrderResponseDto> orderListAllToday = adminOrderService.getOrdersAllToday();
        return ResponseEntity.status(HttpStatus.OK).body(orderListAllToday);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> UpdateOrderState(
        @PathVariable Long orderId,
        @RequestBody OrderStateRequestDto orderStateRequestDto
    ) {
        OrderResponseDto orderResponseDto =
            adminOrderService.updateOrderState(orderId, orderStateRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> CancelOrder(
        @PathVariable Long orderId
    ) {
        adminOrderService.cancelOrder(orderId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


}
