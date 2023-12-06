package com.example.dp.domain.review.dto.response;

import com.example.dp.domain.review.entity.Review;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    private ReviewResponseDto(final Long id, final String content, final LocalDateTime createdAt,
        final LocalDateTime modifiedAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
