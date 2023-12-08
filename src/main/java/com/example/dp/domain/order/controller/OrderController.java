package com.example.dp.domain.order.controller;


import com.example.dp.domain.order.dto.response.OrderResponseDto;
import com.example.dp.domain.order.service.impl.OrderServiceImpl;
import com.example.dp.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping()
    public ResponseEntity<OrderResponseDto> createOrders(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {

        OrderResponseDto orderResponseDto = orderService.createOrder(userDetailsImpl.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrders(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @PathVariable Long orderId
    ) {

        orderService.deleteOrder(userDetailsImpl.getUser(), orderId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("")

    public ResponseEntity<List<OrderResponseDto>> getOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        List<OrderResponseDto> orderResponseDtoList = orderService.getOrder(
            userDetailsImpl.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtoList);
    }


}
