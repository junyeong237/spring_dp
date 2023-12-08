package com.example.dp.domain.menulike.controller;


import com.example.dp.domain.menulike.service.impl.MenuLikeServiceImpl;
import com.example.dp.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuLikeController {

    private final MenuLikeServiceImpl menuLikeService;

    @PutMapping("/{menuId}/like")
    public ResponseEntity<Void> clickLikeMenu(
        @PathVariable Long menuId,
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ){
        menuLikeService.clickLike(userDetailsImpl.getUser(),menuId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }

}
