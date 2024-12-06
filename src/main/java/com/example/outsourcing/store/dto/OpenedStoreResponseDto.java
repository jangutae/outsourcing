package com.example.outsourcing.store.dto;

import com.example.outsourcing.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class OpenedStoreResponseDto {

    private Long storeId;

    private Long userId;

    private String storeName;

    private Integer minPrice;

    private LocalTime openTime;

    private LocalTime closeTime;

    public OpenedStoreResponseDto(Store store) {

        this.storeId = store.getId();
        this.userId = store.getUser().getId();
        this.storeName = store.getStoreName();
        this.minPrice = store.getMinPrice();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();

    }

}
