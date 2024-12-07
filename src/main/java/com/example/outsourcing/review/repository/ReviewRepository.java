package com.example.outsourcing.review.repository;

import com.example.outsourcing.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

        @Query("select r " +
                "from Review r " +
                "where r.store.id = :storeId "+
                " and r.star between :minStar and :maxStar " +
                " order by r.createdAt desc ")
        List<Review> findAllReviewAndWithoutMineByStoreId(
                @Param("storeId") Long storeId,
                @Param("minStar") Integer minStar,
                @Param("maxStar") Integer maxStar);
}
