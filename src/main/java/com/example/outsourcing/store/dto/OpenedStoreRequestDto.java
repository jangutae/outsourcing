package com.example.outsourcing.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class OpenedStoreRequestDto {
    @NotBlank(message = "가게명을 입력해주세요")
    private String storeName;
    @NotNull(message = "최소 주문 금액을 입력해주세요")
    private Integer minPrice;
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):(0[1-9]|[0-5][0-9])$", message = "올바른 형식을 입력해주세요")
    private LocalTime openTime;
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):(0[1-9]|[0-5][0-9])$", message = "올바른 형식을 입력해주세요")
    private LocalTime closeTime;
}
