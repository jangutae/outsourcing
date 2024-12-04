package com.example.outsourcing.menu.service;

import com.example.outsourcing.common.constants.AccountRole;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.menu.dto.CreateMenuRequestDto;
import com.example.outsourcing.menu.dto.MenuResponseDto;
import com.example.outsourcing.menu.dto.UpdateMenuRequestDto;
import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.menu.enums.StateType;
import com.example.outsourcing.menu.exception.MenuErrorCode;
import com.example.outsourcing.menu.repository.MenuRepository;
import com.example.outsourcing.store.entity.Store;
import com.example.outsourcing.store.repository.StoreRepository;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;


    public MenuResponseDto createdMenu(Long userId, Long storeId, CreateMenuRequestDto requestDto) {
        User user = userRepository.findByIdOrElseThrows(userId);

        if (!user.getRole().equals(AccountRole.BOSS)) {
            throw new CustomException(MenuErrorCode.NOT_ACCESS);
        }

        Store store = storeRepository.findByOrElseThrow(storeId);
        Menu menu = new Menu(user, store, requestDto.menuName(), requestDto.price(), StateType.ORDER_POSSIBLE);
        menuRepository.save(menu);

        return MenuResponseDto.toDto(menu);
    }

    @Transactional
    public MenuResponseDto updatedMenu(Long userId, Long storeId, Long menuId,  UpdateMenuRequestDto requestDto) {
        Menu menu = menuRepository.findMenuById(userId, storeId, menuId);

        if (!menu.getUser().getId().equals(userId)) {
            throw new CustomException(MenuErrorCode.NOT_ACCESS);
        }

        if (!menu.getStore().getId().equals(storeId)) {
            throw new CustomException(MenuErrorCode.NOT_ACCESS);
        }

        if () {
            throw new CustomException(MenuErrorCode.NOT_FOUND);
        }


        menu.update(requestDto.menuName(), requestDto.price());
        menuRepository.save(menu);

        return MenuResponseDto.toDto(menu);
    }

    public String updatedState(Long userId, Long storeId, Long menuId) {
        Menu menu = menuRepository.findMenuById(userId, storeId, menuId);

        if (menu.getState().equals(StateType.ORDER_POSSIBLE)) {
            menu.setState(StateType.DELETED);
        }

        return menu.getState().toString();
    }

    /* 가게 조회 시 전체 메뉴 조회 (가게 메뉴 조회시)
    메뉴 상태가 DELETE 인 경우는 제외
     */
    public List<Menu> findAllWithoutDeleteByStoreId(Long storeId) {
        List<Menu> allByStoreId = menuRepository.findAllByStoreId(storeId);
        List<Menu> withoutDelete = new ArrayList<>();

        for (int i = 0; i < allByStoreId.size(); i++) {
            if (allByStoreId.get(i).getState().equals(StateType.ORDER_POSSIBLE)) {
                withoutDelete.add(allByStoreId.get(i));
            }
        }
        return withoutDelete;
    }

    /* 가게 조회 시 전체 메뉴 조회 (주문 메뉴 조회시)
    메뉴 상태 관계 없이 전제 조회
    */
    public List<Menu> findAllByStoreId(Long storeId) {
        return menuRepository.findAllByStoreId(storeId);
    }
}
