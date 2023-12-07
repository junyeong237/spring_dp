package com.example.dp.domain.cart.controller;


import com.example.dp.domain.cart.dto.request.CartDeleteRequestMenuDto;
import com.example.dp.domain.cart.dto.request.CartRequestMenuDto;
import com.example.dp.domain.cart.dto.response.CartResponseDto;
import com.example.dp.domain.cart.service.CartService;
import com.example.dp.domain.cart.service.impl.CartServiceImpl;
import com.example.dp.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartService;

    @GetMapping
    public ResponseEntity<List<CartResponseDto>> getCart(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {

        List<CartResponseDto> cartList = cartService.getCart(userDetailsImpl.getUser());
        return ResponseEntity.ok(cartList);


    }

    @PostMapping("/menus")
    public ResponseEntity<CartResponseDto> postCart(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @RequestBody CartRequestMenuDto cartRequestMenuDto
    ){
        CartResponseDto cartResponseDto = cartService.postCart(userDetailsImpl.getUser(),
            cartRequestMenuDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponseDto);

    }

    @DeleteMapping("/menus")
    public ResponseEntity<?> deleteCartMenu(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
        @RequestBody CartDeleteRequestMenuDto cartDeleteRequestMenuDto
    ){
        cartService.deleteCartMenu(userDetailsImpl.getUser(),cartDeleteRequestMenuDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);

    }

    @DeleteMapping("/menus/clear")
    public ResponseEntity<?> deleteUserCartAll(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ){
        cartService.deleteCart(userDetailsImpl.getUser());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
