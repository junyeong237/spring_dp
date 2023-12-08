package com.example.dp.domain.menulike.service.impl;

import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.menu.exception.MenuErrorCode;
import com.example.dp.domain.menu.exception.NotFoundMenuException;
import com.example.dp.domain.menu.repository.MenuRepository;
import com.example.dp.domain.menulike.entity.MenuLike;
import com.example.dp.domain.menulike.repository.MenuLikeRepository;
import com.example.dp.domain.menulike.service.MenuLikeService;
import com.example.dp.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuLikeServiceImpl implements MenuLikeService {

    private final MenuRepository menuRepository;
    private final MenuLikeRepository menuLikeRepository;
    @Override
    public void clickLike(final User user, final Long menuId) {

        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(()->new NotFoundMenuException(MenuErrorCode.NOT_FOUND_MENU));
        List<MenuLike> menuLikeList = menuLikeRepository.findAllByMenu(menu);

        menuLikeRepository.findByUserAndMenu(user,menu)
            .ifPresentOrElse(
                menuLike -> {
                    menuLikeList.remove(menuLike);
                    menuLikeRepository.delete(menuLike);
                    menu.subLikeCounts();
                },
                () -> {
                    MenuLike menuLike = MenuLike.builder() // 좋아요를 누른적이 없으면 좋아요 생성
                        .user(user)
                        .menu(menu)
                        .build();
                    menu.addMenuLike(menuLike);

                    menuLikeRepository.save(menuLike);
                }
            );


    }


}
