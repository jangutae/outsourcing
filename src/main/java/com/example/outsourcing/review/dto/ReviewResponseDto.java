package com.example.outsourcing.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ReviewResponseDto {

    private Long id;
    private Integer star;
    private String contents;

    private Long userId;
    private Long storeId;
    private String storeName;
    private String menuName;


    private LocalDateTime createdAt;

}
