package com.example.outsourcing.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class OpenedStoreRequestDto {
    @NotBlank(message = "가게명을 입력해주세요")
    private String storeName;

    @NotNull(message = "최소 주문 금액을 입력해주세요")
    private Integer minPrice;

    private LocalTime openTime;

    private LocalTime closeTime;
}
