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

    Review findByOrderId(Long orderId);

    @Query(value = "select review " +
            "from Review review " +
            "where review.storeId =:storeId and review.userId != :userId " +
            "order by review.createdAt desc")
    List<Review> findAllByStoreIdOrderByCreatedAtDesc(
            Long storeId, Long userId);


    @Query(value = "select review " +
            "from Review review " +
            "where review.storeId=:storeId and review.userId !=:userId and review.star between (:star1) and (:star2) " +
            "order by review.createdAt desc ")
    List<Review> findAllByStoreIdAndByStarBetween(
            Long storeId,
            Long userId,
            Integer star1,
            Integer star2);


}
