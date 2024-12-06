package com.example.outsourcing.store.dto;

import com.example.outsourcing.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreInfoResponseDto {
    private Long storeId;

    private String storeName;

    public StoreInfoResponseDto(Store store) {

        this.storeId = store.getId();
        this.storeName = store.getStoreName();
    }

    public static StoreInfoResponseDto toDto(Store store) {
        return new StoreInfoResponseDto(store);
    }
}
