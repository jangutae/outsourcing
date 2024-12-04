package com.example.outsourcing.menu.dto;

import jakarta.validation.constraints.NotNull;


public record CreateMenuRequestDto(

        @NotNull(message = "메뉴이름은 필수값 입니다.")
        String menuName,

        @NotNull(message = "메뉴 가격은 필수값 입니다.")
        Integer price
) {
}
