package com.example.dp.domain.review.service.impl;

import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.order.entity.OrderState;
import com.example.dp.domain.order.repository.OrderRepository;
import com.example.dp.domain.review.dto.request.ReviewRequestDto;
import com.example.dp.domain.review.dto.response.ReviewResponseDto;
import com.example.dp.domain.review.entity.Review;
import com.example.dp.domain.review.exception.ForbiddenAccessReviewException;
import com.example.dp.domain.review.exception.ForbiddenCreateReviewException;
import com.example.dp.domain.review.exception.NotFoundOrderException;
import com.example.dp.domain.review.exception.NotFoundReviewException;
import com.example.dp.domain.review.exception.ReviewAlreadyExistsException;
import com.example.dp.domain.review.exception.ReviewErrorCode;
import com.example.dp.domain.review.repository.ReviewRepository;
import com.example.dp.domain.review.service.ReviewService;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.exception.NotFoundUserException;
import com.example.dp.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public ReviewResponseDto createReview(
        final Long orderId,
        final ReviewRequestDto reviewRequestDto,
        final User user) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundOrderException(ReviewErrorCode.NOT_FOUND_ORDER));

        if(order.getState() != OrderState.COMPLETED){
            throw new ForbiddenCreateReviewException(ReviewErrorCode.FORBIDDEN_CREATE);
        }

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ForbiddenAccessReviewException(ReviewErrorCode.FORBIDDEN_ACCESS);
        }

        if (reviewRepository.existsByOrder(order)) {
            throw new ReviewAlreadyExistsException(ReviewErrorCode.EXISTS_REVIEW);
        }

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
            .orElseThrow(() -> new NotFoundReviewException(ReviewErrorCode.NOT_FOUND_REVIEW));

        if (!review.getOrder().getUser().getId().equals(user.getId())) {
            throw new ForbiddenAccessReviewException(ReviewErrorCode.FORBIDDEN_ACCESS);
        }

        review.updateContent(reviewRequestDto.getContent());

        return toDto(review);
    }

    @Override
    @Transactional
    public void deleteReview(final Long reviewId, final User user) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new NotFoundReviewException(ReviewErrorCode.NOT_FOUND_REVIEW));

        if (!review.getOrder().getUser().getId().equals(user.getId())) {
            throw new ForbiddenAccessReviewException(ReviewErrorCode.FORBIDDEN_ACCESS);
        }

        reviewRepository.delete(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getUserReviews(final Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundUserException(ReviewErrorCode.NOT_FOUND_USER));

        return user.getReviews()
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(this::toDto).toList();
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
