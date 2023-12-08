package com.example.dp.domain.admin.service.impl;

import com.example.dp.domain.admin.service.AdminReviewService;
import com.example.dp.domain.review.entity.Review;
import com.example.dp.domain.review.exception.NotFoundReviewException;
import com.example.dp.domain.review.exception.ReviewErrorCode;
import com.example.dp.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminReviewServiceImpl implements AdminReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public void deleteReview(final Long reviewId) {
        // 리뷰가 있는지 확인
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new NotFoundReviewException(
                ReviewErrorCode.NOT_FOUND_REVIEW));

        reviewRepository.delete(review);
    }
}
