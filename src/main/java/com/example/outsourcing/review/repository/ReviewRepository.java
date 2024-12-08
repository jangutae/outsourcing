package com.example.outsourcing.review.repository;

import com.example.outsourcing.order.entity.Order;
import com.example.outsourcing.review.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 주문번호에 해당하는 Review 찾기
    Review findByOrderId(Long orderId);

    // 가게별 조회 (내 아이디 제외, 최신순으로 정렬 )
    @Query(value = "select review " +
            "from Review review " +
            "where review.storeId =:storeId and review.userId != :userId " +
            "order by review.createdAt desc")
    List<Review> findAllByStoreIdOrderByCreatedAtDesc(
            @Param("storeId") Long storeId, @Param("userId") Long userId);

    // 가게별 조회 (내 아이디 제외. 최신순으로 정렬 )  > 조회할 리뷰가 1개 일 때
    @Query(value = "select review " +
            "from Review review " +
            "where review.storeId=:storeId and review.userId !=:userId and review.star in (:star) " +
            "order by review.createdAt desc " )
    List<Review> findAllByStoreIdAndByStar(Long storeId, Long userId, Integer star);

    // 가게별 조회 (내 아이디 제외. 최신순으로 정렬 )  > 조회할 리뷰가 2개 일 때 (해당 범위로 조회)
    @Query(value = "select review " +
            "from Review review " +
            "where review.storeId=:storeId and review.userId !=:userId and review.star between (:star1) and (:star2) " +
            "order by review.createdAt desc ")
    List<Review> findAllByStoreIdAndByStarBetween(
            @Param("storeId") Long storeId,
            @Param("userId") Long userId,
            @Param("star1") Integer star1,
            @Param("star2") Integer star2);


}
