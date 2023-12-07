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

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Override
    public ReviewResponseDto createReview(
        final Long orderId,
        final ReviewRequestDto reviewRequestDto,
        final User user) {
        Order findOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));

        ReviewValidator.validOrderBy(findOrder, user);
        ReviewValidator.validExistReview(reviewRepository.existsByOrder(findOrder));

        Review review = Review.builder()
            .content(reviewRequestDto.getContent())
            .order(findOrder)
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
            .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        ReviewValidator.validOrderBy(review.getOrder(), user);

        review.updateContent(reviewRequestDto.getContent());

        return ReviewResponseDto.builder()
            .id(review.getId())
            .content(review.getContent())
            .createdAt(review.getCreatedAt())
            .modifiedAt(review.getModifiedAt())
            .build();
    }
}
