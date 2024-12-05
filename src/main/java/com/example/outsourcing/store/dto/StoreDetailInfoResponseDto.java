package com.example.outsourcing.store.dto;

import com.example.outsourcing.menu.dto.MenuResponseDto;
import com.example.outsourcing.store.entity.Store;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreDetailInfoResponseDto {
    private Long id;
    private String storeName;
    private List<MenuResponseDto> menuList;
    private Integer minPrice;
    private String openTime;
    private String closeTime;

    public StoreDetailInfoResponseDto(Store store, List<MenuResponseDto> list) {

        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.menuList = list;
        this.minPrice = store.getMinPrice();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();

    }
}
