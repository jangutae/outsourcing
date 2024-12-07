package com.example.outsourcing.review.dto;

import lombok.Getter;

@Getter
public record ReviewRequestDto(
        Integer star,
        String contents
)
{ }

