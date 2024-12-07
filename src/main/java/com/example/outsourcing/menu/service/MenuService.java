package com.example.outsourcing.menu.service;

import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.MenuErrorCode;
import com.example.outsourcing.common.exception.StoreErrorCode;
import com.example.outsourcing.menu.dto.CreateMenuRequestDto;
import com.example.outsourcing.menu.dto.MenuResponseDto;
import com.example.outsourcing.menu.dto.UpdateMenuRequestDto;
import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.menu.repository.MenuRepository;
import com.example.outsourcing.store.entity.Store;
import com.example.outsourcing.store.repository.StoreRepository;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    // 메뉴 생성
    @Transactional
    public MenuResponseDto createdMenu(Long userId, Long storeId, CreateMenuRequestDto requestDto) {
        User user = userRepository.findByIdOrElseThrows(userId);
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND));
        Menu menu = new Menu(store, requestDto.menuName(), requestDto.price(), Menu.MenuState.ORDER_POSSIBLE);

        if (!menu.isBossAccessPossible(user)) {
            throw new CustomException(MenuErrorCode.NOT_ACCESS);
        }

        menuRepository.save(menu);
        return MenuResponseDto.toDto(menu);
    }

    // 메뉴 수정
    @Transactional
    public MenuResponseDto updatedMenu(Long userId, Long menuId, UpdateMenuRequestDto requestDto) {
        User user = userRepository.findByIdOrElseThrows(userId);
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);

        // 사장은 다른 사장 계정에 접근 불가
        if (!menu.isBossAccessPossible(user)) {
            throw new CustomException(MenuErrorCode.NOT_ACCESS);
        }

        menu.update(requestDto.menuName(), requestDto.price());
        menuRepository.save(menu);

        return MenuResponseDto.toDto(menu);
    }

    // 메뉴 상태 변경
    @Transactional
    public String updatedState(Long userId, Long menuId) {
        User user = userRepository.findByIdOrElseThrows(userId);
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);

        if (!menu.isBossAccessPossible(user)) {
            throw new CustomException(MenuErrorCode.NOT_ACCESS);
        }

        if (menu.getState().equals(Menu.MenuState.ORDER_POSSIBLE)) {
            menu.setState(Menu.MenuState.DELETED);
        } else {
            menu.setState(Menu.MenuState.ORDER_POSSIBLE);
        }

        menuRepository.save(menu);
        return menu.getState().toString();
    }

    /* 가게 조회 시 전체 메뉴 조회 (가게 상세 조회 시 메뉴 전체 조회)
    메뉴 상태가 DELETE 인 경우는 제외
     */
    public List<Menu> findAllWithoutDeleteByStoreId(Long storeId) {
        return menuRepository.findAllByStoreIdAndState(storeId, Menu.MenuState.DELETED);
    }
}
