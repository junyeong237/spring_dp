package com.example.dp.domain.cart.controller;


import com.example.dp.domain.cart.dto.response.CartResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    //private final CartService cartService;

    @GetMapping
    public List<CartResponseDto> getCart() {

        //cartService.getCart();
        return null;//임시용

    }

}
