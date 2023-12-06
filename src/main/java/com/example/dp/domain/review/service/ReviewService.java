package com.example.dp.domain.review.service;

import com.example.dp.domain.review.dto.request.ReviewRequestDto;
import com.example.dp.domain.review.dto.response.ReviewResponseDto;
import com.example.dp.domain.user.entity.User;

public interface ReviewService {

    ReviewResponseDto createReview(Long orderId, ReviewRequestDto reviewRequestDto, User user);

}
