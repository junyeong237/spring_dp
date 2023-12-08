package com.example.dp.domain.admin.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.dp.domain.admin.service.AdminReviewService;
import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.order.repository.OrderRepository;
import com.example.dp.domain.review.entity.Review;
import com.example.dp.domain.review.exception.NotFoundReviewException;
import com.example.dp.domain.review.repository.ReviewRepository;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.repository.UserRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class AdminReviewServiceImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    AdminReviewService adminReviewService;

    FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setup() {
        fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .build();
        User user = createAndSaveUser();
        Order order = createAndSaveOrder(user, OrderState.COMPLETED);
        createAndSaveReview(order, user);
    }

    @AfterEach
    void post(){
        reviewRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("리뷰를 삭제한다.")
    @Transactional
    public void deleteReview(){
        // given
        Long reviewId = 1L;

        // when
        adminReviewService.deleteReview(reviewId);

        // then
        assertThat(reviewRepository.findById(reviewId)).isEmpty();
    }

    @Test
    @DisplayName("없는 리뷰를 삭제한다.")
    @Transactional
    public void deleteNoneExistsReview(){
        // given
        Long reviewId = 100L;

        // when - then
        assertThatThrownBy(() -> adminReviewService.deleteReview(reviewId))
            .isInstanceOf(NotFoundReviewException.class);
    }

    private User createAndSaveUser() {
        User sample = fixtureMonkey.giveMeBuilder(User.class)
            .setNull("reviews")
            .sample();
        return userRepository.save(sample);
    }

    private Order createAndSaveOrder(final User user, final OrderState orderState) {
        Order sample = fixtureMonkey.giveMeBuilder(Order.class)
            .setNull("orderMenuList")
            .set("user", user)
            .set("state", orderState)
            .sample();
        return orderRepository.save(sample);
    }

    private Review createAndSaveReview(Order order, User user) {
        Review sample = fixtureMonkey.giveMeBuilder(Review.class)
            .set("order", order)
            .set("user", user)
            .sample();
        return reviewRepository.save(sample);
    }
}