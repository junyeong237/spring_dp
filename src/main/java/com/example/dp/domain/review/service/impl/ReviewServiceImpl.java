package com.example.dp.domain.review.service.impl;

import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.repository.OrderRepository;
import com.example.dp.domain.review.dto.request.ReviewRequestDto;
import com.example.dp.domain.review.dto.response.ReviewResponseDto;
import com.example.dp.domain.review.entity.Review;
import com.example.dp.domain.review.repository.ReviewRepository;
import com.example.dp.domain.review.service.ReviewService;
import com.example.dp.domain.review.validator.ReviewValidator;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private static final String NOT_FOUND_USER = "사용자를 찾을 수 없습니다.";
    private static final String NOT_FOUND_ORDER = "해당 주문을 찾을 수 없습니다.";
    private static final String NOT_FOUND_REVIEW = "해당 리뷰를 찾을 수 없습니다.";

    @Override
    public ReviewResponseDto createReview(
        final Long orderId,
        final ReviewRequestDto reviewRequestDto,
        final User user) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException(NOT_FOUND_ORDER));

        ReviewValidator.validOrderBy(order, user);
        ReviewValidator.validExistReview(reviewRepository.existsByOrder(order));

        Review review = Review.builder()
            .user(user)
            .content(reviewRequestDto.getContent())
            .order(order)
            .build();

        review = reviewRepository.save(review);

        return toDto(review);
    }

    @Override
    @Transactional
    public ReviewResponseDto updateReview(
        final Long reviewId,
        final ReviewRequestDto reviewRequestDto,
        final User user) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException(NOT_FOUND_REVIEW));

        ReviewValidator.validOrderBy(review.getOrder(), user);

        review.updateContent(reviewRequestDto.getContent());

        return toDto(review);
    }

    @Override
    public void deleteReview(final Long reviewId, final User user) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException(NOT_FOUND_REVIEW));

        ReviewValidator.validOrderBy(review.getOrder(), user);

        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewResponseDto> getUserReviews(final Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(NOT_FOUND_USER));

        return user.getReviews()
            .stream()
            .map(this::toDto)
            .toList();
    }

    private ReviewResponseDto toDto(Review review){
        return ReviewResponseDto.builder()
            .id(review.getId())
            .writtenBy(review.getUser().getUsername())
            .content(review.getContent())
            .createdAt(review.getCreatedAt())
            .modifiedAt(review.getModifiedAt())
            .build();
    }
}
