package com.example.dp.domain.review.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ReviewResponseDto {

    private final Long id;
    private final String writtenBy;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    private ReviewResponseDto(final Long id, final String writtenBy, final String content,
        final LocalDateTime createdAt,
        final LocalDateTime modifiedAt) {
        this.id = id;
        this.writtenBy = writtenBy;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
