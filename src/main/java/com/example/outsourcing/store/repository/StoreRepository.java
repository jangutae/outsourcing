package com.example.outsourcing.store.repository;

import com.example.outsourcing.common.constants.AccountStatus;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.StoreErrorCode;
import com.example.outsourcing.store.entity.StoreState;
import com.example.outsourcing.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findAllByUser_stateAndStoreNameContainsAndStoreState(AccountStatus accountStatus, String text, StoreState storeState);

    Optional<Store> findByIdAndStoreState(Long id, StoreState storeState);

    default Store findByAndStateOrElseThrow(Long id, StoreState storeState) {

        return findByIdAndStoreState(id, storeState).orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND));
    }

    List<Store> findAllByUser_stateAndStoreState(AccountStatus accountStatus,StoreState storeState);

    Integer countStoreByUser_idAndStoreState(Long userId, StoreState storeState);

}
