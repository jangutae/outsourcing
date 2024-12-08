package com.example.outsourcing.store.controller;

import com.example.outsourcing.store.dto.OpenedStoreRequestDto;
import com.example.outsourcing.store.dto.OpenedStoreResponseDto;
import com.example.outsourcing.store.dto.StoreDetailInfoResponseDto;
import com.example.outsourcing.store.dto.StoreInfoResponseDto;
import com.example.outsourcing.store.service.StoreService;
import com.example.outsourcing.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 가게 창업
    @PostMapping
    public ResponseEntity<OpenedStoreResponseDto> openStore(@Validated @RequestBody OpenedStoreRequestDto openedStoreRequestDto, @SessionAttribute(value = "id") Long id) {

        OpenedStoreResponseDto openedStoreResponseDto = storeService.open(id, openedStoreRequestDto);
        return new ResponseEntity<>(openedStoreResponseDto, HttpStatus.CREATED);

    }

    // 가게 전체 조회
    @GetMapping
    public ResponseEntity<List<StoreInfoResponseDto>> showStore() {
        List<StoreInfoResponseDto> storeInfoResponseDtoList = storeService.showStoreList();
        return new ResponseEntity<>(storeInfoResponseDtoList, HttpStatus.OK);
    }

    // 가게 검색 조회
    @GetMapping("/search")
    public ResponseEntity<List<StoreInfoResponseDto>> researchStore(@RequestParam String text) {
        List<StoreInfoResponseDto> storeInfoResponseDtoList = storeService.searchStoreList(text);
        return new ResponseEntity<>(storeInfoResponseDtoList, HttpStatus.OK);
    }

    // 가게 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<StoreDetailInfoResponseDto> storeDetailInfoResponseDto(@PathVariable Long id) {
        StoreDetailInfoResponseDto storeDetailInfoResponseDto = storeService.showDetailStore(id);
        return new ResponseEntity<>(storeDetailInfoResponseDto, HttpStatus.OK);

    }

    // 가게 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStoreInfo(@PathVariable Long id, @RequestBody OpenedStoreResponseDto openedStoreResponseDto, @SessionAttribute("id") Long userId) {

        storeService.updateStoreInfo(userId, id, openedStoreResponseDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 가게 폐업
    @DeleteMapping("/{id}")
    public void closeStore(@PathVariable Long id, @SessionAttribute("id") Long userId) {

        storeService.close(id, userId);
    }
}
