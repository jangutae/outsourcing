package com.example.outsourcing.review.dto;

import com.example.outsourcing.review.entity.Review;


public record ReviewResponseDto(
        Long id,
        Long userId,
        Long storeId,
        Integer star,
        String contents,
        String storeName,
        String menuName
) {

    public static ReviewResponseDto toDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getUser().getId(),
                review.getStore().getId(),
                review.getStar(),
                review.getContents(),
                review.getStore().getStoreName(),
                review.getOrder().getMenu().getMenuName());
    }
}
