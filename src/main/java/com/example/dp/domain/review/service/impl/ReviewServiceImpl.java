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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    private static String NOT_FOUND_ORDER = "해당 주문을 찾을 수 없습니다.";
    private static String NOT_FOUND_REVIEW = "해당 리뷰를 찾을 수 없습니다.";

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
            .content(reviewRequestDto.getContent())
            .order(order)
            .build();

        review = reviewRepository.save(review);

        return ReviewResponseDto.builder()
            .id(review.getId())
            .content(review.getContent())
            .createdAt(review.getCreatedAt())
            .modifiedAt(review.getModifiedAt())
            .build();
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

        return ReviewResponseDto.builder()
            .id(review.getId())
            .content(review.getContent())
            .createdAt(review.getCreatedAt())
            .modifiedAt(review.getModifiedAt())
            .build();
    }

    @Override
    public void deleteReview(final Long reviewId, final User user) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException(NOT_FOUND_REVIEW));

        ReviewValidator.validOrderBy(review.getOrder(), user);

        reviewRepository.delete(review);
    }
}
