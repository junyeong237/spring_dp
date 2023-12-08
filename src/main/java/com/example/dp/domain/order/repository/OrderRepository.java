package com.example.dp.domain.order.repository;

import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserAndState(User user, OrderState state);

    List<Order> findAllByCreatedAtAfter(LocalDateTime afterDate);

    List<Order> findByUser(User user);

}