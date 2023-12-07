package com.example.dp.domain.review.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.shouldHaveThrown;

import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.order.repository.OrderRepository;
import com.example.dp.domain.review.dto.request.ReviewRequestDto;
import com.example.dp.domain.review.dto.response.ReviewResponseDto;
import com.example.dp.domain.review.entity.Review;
import com.example.dp.domain.review.repository.ReviewRepository;
import com.example.dp.domain.review.service.ReviewService;
import com.example.dp.domain.user.UserRole;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.repository.UserRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
        void createReview() {
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
        void createReviewByAnotherUser() {
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
        void createReviewAgain() {
            // given
            User user = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            createAndSaveReview(order, user);
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
        void updateReview() {
            // given
            User user = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            Review review = createAndSaveReview(order, user);
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
        void updateReviewByAnotherUser() {
            // given
            User user = createAndSaveUser();
            User anotherUser = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            Review review = createAndSaveReview(order, user);
            ReviewRequestDto requestDto = new ReviewRequestDto("테스트 내용");

            // when - then
            assertThatThrownBy(
                () -> reviewService.updateReview(review.getId(), requestDto, anotherUser))
                .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("존재하지 않는 리뷰를 수정하는 경우")
        void updateNonExistReview() {
            // given
            User user = createAndSaveUser();
            long noExistReviewId = 10;
            ReviewRequestDto requestDto = new ReviewRequestDto("테스트 내용");

            // when - then
            assertThatThrownBy(
                () -> reviewService.updateReview(noExistReviewId, requestDto, user))
                .isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    @DisplayName("리뷰 삭제 테스트")
    class DeleteReviewTest {

        @Test
        @DisplayName("작성자가 리뷰를 삭제하는 경우")
        void deleteReview() {
            // given
            User user = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            Review review = createAndSaveReview(order, user);

            // when
            reviewService.deleteReview(review.getId(), user);

            // then
            assertThat(reviewRepository.existsById(review.getId())).isEqualTo(false);
        }

        @Test
        @DisplayName("작성자가 아닌 사람이 리뷰를 삭제하는 경우")
        void deleteReviewByAnotherUser() {
            // given
            User user = createAndSaveUser();
            User anotherUser = createAndSaveUser();
            Order order = createAndSaveOrder(user);
            Review review = createAndSaveReview(order, user);

            // when - then
            assertThatThrownBy(
                () -> reviewService.deleteReview(review.getId(), anotherUser))
                .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("존재하지 않는 리뷰를 삭제하는 경우")
        void deleteNonExistReview() {
            // given
            User user = createAndSaveUser();
            long noExistReviewId = 10;

            // when - then
            assertThatThrownBy(
                () -> reviewService.deleteReview(noExistReviewId, user))
                .isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    @DisplayName("리뷰 조회 테스트")
    class ReadReviewTest {

        @Test
        @DisplayName("사용자가 작성한 리뷰 조회")
        @Disabled
        void readUserReview() {
            // given
            int count = 5;
//            User user = createAndSaveUser();
//            for (int i = 0; i < count; i++) {
//                Order order = createAndSaveOrder(user);
//                createAndSaveReview(order, user);
//            }
            User user = User.builder()
                .email("asdas@asd.asd")
                .username("asdasd")
                .password("asdasdasd")
                .role(UserRole.USER)
                .build();
            user = userRepository.save(user);

            for(int i=0;i<count;i++) {
                Order order = Order.builder()
                    .user(user)
                    .state(OrderState.PENDING)
                    .build();
                orderRepository.save(order);
                Review review = Review.builder()
                    .user(user)
                    .order(order)
                    .content("asdsa")
                    .build();
                reviewRepository.save(review);
            }

            // when
            List<ReviewResponseDto> responseDto = reviewService.getUserReviews(user.getId());

            // then
            assertThat(responseDto.size()).isEqualTo(count);
        }
    }

    private User createAndSaveUser() {
        User sample = fixtureMonkey.giveMeBuilder(User.class)
            .setNotNull("*")
            .setNull("id")
            .sample();
        return userRepository.save(sample);
    }

    private Order createAndSaveOrder(User user) {
        Order sample = fixtureMonkey.giveMeBuilder(Order.class)
            .setNotNull("*")
            .setNull("id")
            .setNull("orderMenuList")
            .set("user", user)
            .sample();
        return orderRepository.save(sample);
    }

    private Review createAndSaveReview(Order order, User user) {
        Review sample = fixtureMonkey.giveMeBuilder(Review.class)
            .setNotNull("*")
            .setNull("id")
            .set("order", order)
            .set("user", user)
            .sample();
        return reviewRepository.save(sample);
    }
}