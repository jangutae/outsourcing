package com.example.outsourcing.store.service;

import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.menu.service.MenuService;
import com.example.outsourcing.store.dto.OpenedStoreRequestDto;
import com.example.outsourcing.store.dto.OpenedStoreResponseDto;
import com.example.outsourcing.store.dto.StoreDetailInfoResponseDto;
import com.example.outsourcing.store.dto.StoreInfoResponseDto;
import com.example.outsourcing.store.entity.State;
import com.example.outsourcing.store.entity.Store;
import com.example.outsourcing.store.repository.StoreRepository;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserService userService;
    private final MenuService menuService;

    public OpenedStoreResponseDto open(Long userId, OpenedStoreRequestDto openedStoreRequestDto) {
        /**todo
         * 가게 생성 3개 초과
         */
        User user = userService.findOwnerById(userId);
        Store store = new Store(user, openedStoreRequestDto);
        storeRepository.save(store);
        return new OpenedStoreResponseDto(store);
    }

    public List<StoreInfoResponseDto> showStoreList() {
        List<Store> storeList = storeRepository.findAll();
        return storeList
                .stream()
                .map(StoreInfoResponseDto::toDto)
                .toList();

    }

    public List<StoreInfoResponseDto> searchStoreList(String text) {
        List<Store> storeList = storeRepository.findAllByStoreNameContainsAndState(text, State.OPENED);
        return storeList
                .stream()
                .map(StoreInfoResponseDto::toDto)
                .toList();

    }
    @Transactional
    public StoreDetailInfoResponseDto showDetailStore(Long id) {
        List<Menu> menuList = menuService.findAllByStoreId(id);
        Store store = storeRepository.findByAndStateOrElseThrow(id, State.OPENED);
        store.updateMenu(menuList);
        return new StoreDetailInfoResponseDto(store);

    }
    @Transactional
    public void updateStoreInfo(Long userId, Long id, OpenedStoreResponseDto openedStoreResponseDto) {
        userService.findOwnerById(userId);
        Store store = storeRepository.findById(id).get();
        store.updateInfo(openedStoreResponseDto);

    }
    @Transactional
    public void close(Long id, Long userId) {
        userService.findOwnerById(userId);
        Store store = storeRepository.findById(id).get();
        store.close();

    }
}
