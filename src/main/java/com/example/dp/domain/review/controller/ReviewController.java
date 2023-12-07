package com.example.dp.domain.review.controller;

import com.example.dp.domain.review.dto.request.ReviewRequestDto;
import com.example.dp.domain.review.dto.response.ReviewResponseDto;
import com.example.dp.domain.review.service.ReviewService;
import com.example.dp.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getUserReviews(@PathVariable Long userId){
        List<ReviewResponseDto> resultDto = reviewService.getUserReviews(userId);
        return ResponseEntity.ok(resultDto);
    }

    @PostMapping("/reviews/{orderId}")
    public ResponseEntity<ReviewResponseDto> createReview(
        @PathVariable Long orderId,
        @RequestBody ReviewRequestDto reviewRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        ReviewResponseDto responseDto = reviewService.createReview(orderId, reviewRequestDto,
            userDetailsImpl.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
        @PathVariable Long reviewId,
        @RequestBody ReviewRequestDto reviewRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        ReviewResponseDto responseDto = reviewService.updateReview(reviewId, reviewRequestDto,
            userDetailsImpl.getUser());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Long> deleteReview(
        @PathVariable Long reviewId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.deleteReview(reviewId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

}
