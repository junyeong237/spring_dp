package com.example.dp.domain.cart.repository;

import com.example.dp.domain.cart.entity.Cart;
import com.example.dp.domain.menu.entity.Menu;
import com.example.dp.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);

    Optional<Cart> findByUserAndMenu(User user, Menu menu);


}
