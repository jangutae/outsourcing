package com.example.outsourcing.store.dto;

import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.store.entity.Store;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreDetailInfoResponseDto {
    private Long id;
    private String storeName;
    private List<Menu> menuList;
    private Integer minPrice;
    private String openTime;
    private String closeTime;

    public StoreDetailInfoResponseDto(Store store) {
        this.id= store.getId();
        this.storeName=store.getStoreName();
        this.menuList=store.getMenuList();
        this.minPrice=store.getMinPrice();
        this.openTime=store.getOpenTime();
        this.closeTime=store.getCloseTime();
    }
}
