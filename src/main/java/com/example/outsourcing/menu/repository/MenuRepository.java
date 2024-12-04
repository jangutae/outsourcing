package com.example.outsourcing.menu.repository;


import com.example.outsourcing.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findMenuById(Long userId, Long storeId, Long menuId);

    List<Menu> findAllByStoreId(Long storeId);
}
