package com.example.outsourcing.menu.controller;

import com.example.outsourcing.menu.dto.CreateMenuRequestDto;
import com.example.outsourcing.menu.dto.MenuResponseDto;
import com.example.outsourcing.menu.dto.UpdateMenuRequestDto;
import com.example.outsourcing.menu.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 Menu 생성 및 수정에 대한 요청 및 응답 로직 수행
 */
@RestController
@RequestMapping("/stores/{storeId}")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /** 메뉴 생성
     *
     * @param storeId 스토어 정보를 받아옵니다.
     * @param requestDto  {@link CreateMenuRequestDto}
     * @param httpServletRequest 세션값을 가져와서 userId 를 반환함
     * @return {@link ResponseEntity} 형태로 에러코드와 body 를 반환함
     */
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

    /** 메뉴 생성
     *
     * @param storeId 스토어 정보를 받아옵니다.
     * @param menuId 메뉴 정보를 받아옵니다.
     * @param requestDto  {@link UpdateMenuRequestDto}
     * @param httpServletRequest 세션값을 가져와서 userId 를 반환함
     * @return {@link ResponseEntity} 형태로 에러코드와 body 를 반환함
     */
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

    /** 메뉴 생성
     *
     * @param storeId 스토어 정보를 받아옵니다.
     * @param menuId 메뉴 정보를 받아옵니다.
     * @param httpServletRequest 세션값을 가져와서 userId 를 반환함
     * @return {@link ResponseEntity} 형태로 에러코드와 body 를 반환함
     */
    @PatchMapping("/menus/{menuId}")
    public ResponseEntity<String> updateMenuState(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession(false);
        Long userId = (Long) session.getAttribute("id");

        String updatedState = menuService.updatedState(userId, storeId, menuId);

        return ResponseEntity.ok().body("메뉴 상태가 " + updatedState + " 으로 변경되었습니다.");
    }
}
