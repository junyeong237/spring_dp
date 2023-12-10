package com.example.dp.domain.review.repository;

import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByOrder(Order findOrder);
}
