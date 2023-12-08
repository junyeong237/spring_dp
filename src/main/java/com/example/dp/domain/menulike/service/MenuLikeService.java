package com.example.dp.domain.menulike.service;

import com.example.dp.domain.user.entity.User;

public interface MenuLikeService {

    void clickLike(User user, Long menuId);


}
