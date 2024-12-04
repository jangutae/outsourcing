package com.example.outsourcing.menu.repository;


import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.menu.exception.MenuErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

   default Menu findMenuByIdOrElseThrow(Long menuId) {
       return findById(menuId).orElseThrow(() -> new CustomException(MenuErrorCode.NOT_FOUND));
   }

    List<Menu> findAllByStoreId(Long storeId);
}
