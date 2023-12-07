package com.example.dp.domain.review.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.repository.OrderRepository;
import com.example.dp.domain.review.dto.request.ReviewRequestDto;
import com.example.dp.domain.review.dto.response.ReviewResponseDto;
import com.example.dp.domain.review.entity.Review;
import com.example.dp.domain.review.repository.ReviewRepository;
import com.example.dp.domain.review.service.ReviewService;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.repository.UserRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReviewServiceImplTest {

    @Autowired
    ReviewService reviewService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ReviewRepository reviewRepository;

    FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setup() {
        fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();
    }

    @Nested
    @DisplayName("리뷰 생성 테스트")
    class CreateReviewTest {

        @Test
        @DisplayName("주문자가 리뷰를 생성하는 경우")
        void 주문자_리뷰_생성() {
            // given
            User user = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            ReviewRequestDto requestDto = new ReviewRequestDto("테스트 내용");

            // when
            ReviewResponseDto responseDto = reviewService.createReview(order.getId(), requestDto,
                user);

            // then
            Review findReview = reviewRepository.findById(responseDto.getId())
                .orElseThrow(RuntimeException::new);
            assertThat(findReview.getId()).isEqualTo(responseDto.getId());
            assertThat(findReview.getContent()).isEqualTo(responseDto.getContent());
        }

        @Test
        @DisplayName("주문자가 아닌 사람이 리뷰를 생성하는 경우")
        void 다른_사람이_리뷰_생성() {
            // given
            User user = createAndSaveUser();
            User anotherUser = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            ReviewRequestDto requestDto = new ReviewRequestDto("테스트 내용");

            // when - then
            assertThatThrownBy(
                () -> reviewService.createReview(order.getId(), requestDto, anotherUser))
                .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("이미 리뷰가 존재한 상태에서 리뷰를 생성하는 경우")
        void 리뷰_중복_생성() {
            // given
            User user = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            createAndSaveReview(order);
            ReviewRequestDto requestDto = new ReviewRequestDto("테스트 내용");

            // when - then
            assertThatThrownBy(
                () -> reviewService.createReview(order.getId(), requestDto, user))
                .isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    @DisplayName("리뷰 수정 테스트")
    class UpdateReviewTest {

        @Test
        @DisplayName("주문자가 리뷰를 수정하는 경우")
        void 주문자_리뷰_수정() {
            // given
            User user = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            Review review = createAndSaveReview(order);
            ReviewRequestDto requestDto = new ReviewRequestDto("테스트 내용");

            // when
            ReviewResponseDto responseDto = reviewService.updateReview(review.getId(), requestDto,
                user);

            // then
            Review findReview = reviewRepository.findById(responseDto.getId())
                .orElseThrow(RuntimeException::new);
            assertThat(findReview.getId()).isEqualTo(responseDto.getId());
            assertThat(findReview.getContent()).isEqualTo(responseDto.getContent());
        }

        @Test
        @DisplayName("주문자가 아닌 사람이 리뷰를 수정하는 경우")
        void 다른_사람이_리뷰_수정() {
            // given
            User user = createAndSaveUser();
            User anotherUser = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            Review review = createAndSaveReview(order);

            ReviewRequestDto requestDto = new ReviewRequestDto("테스트 내용");

            // when - then
            assertThatThrownBy(
                () -> reviewService.updateReview(review.getId(), requestDto, anotherUser))
                .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("존재하지 않는 리뷰를 수정하는 경우")
        void 없는_리뷰_수정() {
            // given
            User user = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            long noExistReviewId = 1;
            ReviewRequestDto requestDto = new ReviewRequestDto("테스트 내용");

            // when - then
            assertThatThrownBy(
                () -> reviewService.updateReview(noExistReviewId, requestDto, user))
                .isInstanceOf(RuntimeException.class);
        }
    }

    private User createAndSaveUser() {
        User sample = fixtureMonkey.giveMeBuilder(User.class)
            .setNotNull("*")
            .sample();
        return userRepository.save(sample);
    }

    private Order createAndSaveOrder(User user) {
        Order sample = fixtureMonkey.giveMeBuilder(Order.class)
            .setNotNull("*")
            .setNull("orderMenuList")
            .set("user", user)
            .sample();
        return orderRepository.save(sample);
    }

    private Review createAndSaveReview(Order order) {
        Review sample = fixtureMonkey.giveMeBuilder(Review.class)
            .setNotNull("*")
            .set("order", order)
            .sample();
        return reviewRepository.save(sample);
    }


}