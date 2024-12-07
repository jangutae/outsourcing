package com.example.outsourcing.review.dto;

import com.example.outsourcing.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        Long id,
        Long userId,
        Long storeId,
        Double star,
        String contents,
        String storeName,
        String menuName
) {

    public static ReviewResponseDto toDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getOrder().getUser().getId(),
                review.getOrder().getStore().getId(),
                review.getStar(),
                review.getContents(),
                review.getOrder().getStore().getStoreName(),
                review.getOrder().getMenu().getMenuName());
    }
}
