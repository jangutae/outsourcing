package com.example.outsourcing.menu.dto;

import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.menu.enums.StateType;
import lombok.Builder;

@Builder
public record MenuResponseDto(
        Long menuId,
        Long storeId,
        String storeName,
        String menuName,
        Integer price,
        StateType state) {

    public static MenuResponseDto toDto(Menu menu) {
        return MenuResponseDto.builder()
                .menuId(menu.getId())
                .storeId(menu.getStore().getId())
                .storeName(menu.getStore().getStoreName())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .state(menu.getState())
                .build();
    }
}
