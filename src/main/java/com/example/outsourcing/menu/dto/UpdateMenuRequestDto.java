package com.example.outsourcing.menu.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record UpdateMenuRequestDto(

        @NotNull(message = "메뉴 이름은 필수값 입니다.")
        @Size(min = 1 , max = 20, message = "메뉴명은 1 ~ 20 자리여야 합니다.")
        String menuName,

        @NotNull(message = "변경할 가격의 메뉴는 필수값 입니다.")
        Integer price
) {

}
