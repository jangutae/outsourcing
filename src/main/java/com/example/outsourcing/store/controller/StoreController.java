package com.example.outsourcing.store.controller;

import com.example.outsourcing.store.dto.OpenedStoreRequestDto;
import com.example.outsourcing.store.dto.OpenedStoreResponseDto;
import com.example.outsourcing.store.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 가게 창업
    @PostMapping
    public ResponseEntity<OpenedStoreResponseDto> openStore(@Validated @RequestBody OpenedStoreRequestDto openedStoreRequestDto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("id");
        OpenedStoreResponseDto openedStoreResponseDto = storeService.open(userId, openedStoreRequestDto);
        return new ResponseEntity<>(openedStoreResponseDto, HttpStatus.CREATED);

    }
}
