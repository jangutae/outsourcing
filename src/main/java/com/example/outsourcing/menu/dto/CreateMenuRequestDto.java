package com.example.outsourcing.menu.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record CreateMenuRequestDto(

        @NotNull(message = "메뉴 이름은 필수값 입니다.")
        @Size(min = 1 , max = 20, message = "메뉴명은 1 ~ 20 자리여야 합니다.")
        String menuName,

        @NotNull(message = "메뉴 가격은 필수값 입니다.")
        Integer price
) {
}
