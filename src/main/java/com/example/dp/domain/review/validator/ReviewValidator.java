package com.example.dp.domain.review.validator;

import com.example.dp.domain.order.entity.Order;
import com.example.dp.domain.user.entity.User;

public class ReviewValidator {

    public static void validExistReview(boolean hasReview) {
        if (hasReview) {
            throw new RuntimeException("이미 리뷰가 존재합니다.");
        }
    }

    public static void validOrderBy(final Order order, final User user) {
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("주문자만 리뷰를 작성/수정할 수 있습니다.");
        }
    }
}
