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
//    private String userName;
//    private String storeName;
    private Long storeId;
    private String menuName;


    private LocalDateTime createdAt;

}
