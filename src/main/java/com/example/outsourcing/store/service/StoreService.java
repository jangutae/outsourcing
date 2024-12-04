package com.example.outsourcing.store.service;

import com.example.outsourcing.store.dto.OpenedStoreRequestDto;
import com.example.outsourcing.store.dto.OpenedStoreResponseDto;
import com.example.outsourcing.store.entity.Store;
import com.example.outsourcing.store.repository.StoreRepository;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserService userService;

    public OpenedStoreResponseDto open(Long userId, OpenedStoreRequestDto openedStoreRequestDto) {
        /**todo
         * 가게 생성 3개 초과
         */
        User user = userService.findOwnerById(userId);
        Store store = new Store(user, openedStoreRequestDto);
        storeRepository.save(store);
        return new OpenedStoreResponseDto(store);
    }
}
