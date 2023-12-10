package com.example.dp.domain.review.service;

import com.example.dp.domain.review.dto.request.ReviewRequestDto;
import com.example.dp.domain.review.dto.response.ReviewResponseDto;
import com.example.dp.domain.user.entity.User;
import java.util.List;

public interface ReviewService {

    ReviewResponseDto createReview(Long orderId, ReviewRequestDto reviewRequestDto, User user);

    ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto reviewRequestDto, User user);

    void deleteReview(Long reviewId, User user);

    List<ReviewResponseDto> getUserReviews(Long userId);

    List<ReviewResponseDto> getAllReviews();
}
