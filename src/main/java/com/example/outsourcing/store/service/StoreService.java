package com.example.outsourcing.store.service;

import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.StoreErrorCode;
import com.example.outsourcing.menu.dto.MenuResponseDto;
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

        User user = userService.findOwnerById(userId);
        Store store = new Store(user, openedStoreRequestDto);
        Integer storeCount = storeRepository.countStoreByUser_idAndState(userId, State.OPENED);
        if (storeCount > 2) {
            throw new CustomException(StoreErrorCode.OUT_RANGE);
        }

        storeRepository.save(store);
        return new OpenedStoreResponseDto(store);
    }

    public List<StoreInfoResponseDto> showStoreList() {

        List<Store> storeList = storeRepository.findAllByState(State.OPENED);
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

        List<Menu> menuList = menuService.findAllWithoutDeleteByStoreId(id);
        List<MenuResponseDto> list = menuList.stream().map(MenuResponseDto::toDto).toList();
        Store store = storeRepository.findByAndStateOrElseThrow(id, State.OPENED);
        return new StoreDetailInfoResponseDto(store, list);

    }

    @Transactional
    public void updateStoreInfo(Long userId, Long id, OpenedStoreResponseDto openedStoreResponseDto) {

        userService.findOwnerById(userId);
        Store store = storeRepository.findByAndStateOrElseThrow(id, State.OPENED);
        store.updateInfo(openedStoreResponseDto);

    }

    @Transactional
    public void close(Long id, Long userId) {

        userService.findOwnerById(userId);
        Store store = storeRepository.findByAndStateOrElseThrow(id, State.OPENED);
        store.close();

    }
}
