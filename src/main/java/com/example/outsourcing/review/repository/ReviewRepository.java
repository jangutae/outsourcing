package com.example.outsourcing.review.repository;

import com.example.outsourcing.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

//    Review findByOrderId(Long orderId);
//
//    @Query(value = "select review " +
//            "from Review review " +
//            "where review.storeId =:storeId and review.userId != :userId " +
//            "order by review.createdAt desc")
//    List<Review> findAllByStoreIdOrderByCreatedAtDesc(
//            @Param("storeId") Long storeId,
//            @Param("userId") Long userId);
//
//
//    @Query(value = "select review " +
//            "from Review review " +
//            "where review.storeId=:storeId and review.userId !=:userId and review.star between (:star1) and (:star2) " +
//            "order by review.createdAt desc ")
//    List<Review> findAllByStoreIdAndByStarBetween(
//            @Param("storeId") Long storeId,
//            @Param("userId") Long userId,
//            @Param("star1") Integer star1,
//            @Param("star2") Integer star2);


    @Query(value = "select review " +
            "from Review review " +
            "where review.order.store.id = :storeId " +
            " and review.star >= :minStar and review.star <= :maxStar " +
            " order by Review.createdAt desc ")
    List<Review> findAllReviewAndWithoutMineByStoreId(
            @Param("storeId") Long storeId,
            @Param("minStar") Double minStar,
            @Param("maxStar") Double maxStar
    );



//    List<Review> findAllByStoreIdAndStarBetween(Long storeId, Double minStar, Double maxStar);


}
