package com.example.outsourcing.menu.controller;

import com.example.outsourcing.menu.dto.CreateMenuRequestDto;
import com.example.outsourcing.menu.dto.MenuResponseDto;
import com.example.outsourcing.menu.dto.UpdateMenuRequestDto;
import com.example.outsourcing.menu.service.MenuService;
import com.example.outsourcing.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stores/{storeId}")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/menus")
    public ResponseEntity<MenuResponseDto> createMenu(
            @PathVariable Long storeId,
            @Valid @RequestBody CreateMenuRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession(false);
        Long userId = (Long) session.getAttribute("id");

        MenuResponseDto menuResponseDto = menuService.createdMenu(userId, storeId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(menuResponseDto);
    }

    @PutMapping("/menus/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @Valid @RequestBody UpdateMenuRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession(false);
        Long userId = (Long) session.getAttribute("id");

        MenuResponseDto menuResponseDto = menuService.updatedMenu(userId, storeId, menuId, requestDto);

        return ResponseEntity.ok().body(menuResponseDto);
    }

    @PatchMapping("/menus/{menuId}")
    public ResponseEntity<String> updateMenuState(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession(false);
        Long userId = (Long) session.getAttribute("id");

        String updatedState = menuService.updatedState(userId, storeId, menuId);

        return ResponseEntity.ok().body("메뉴 상태가 " + updatedState + "으로 변경되었습니다.");
    }
}
