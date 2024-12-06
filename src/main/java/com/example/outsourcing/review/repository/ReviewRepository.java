package com.example.outsourcing.review.repository;

import com.example.outsourcing.order.entity.Order;
import com.example.outsourcing.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByOrderIdOrderByCreatedAtDesc(Long orderId);

    List<Review> order(Order order);

    Review findByOrderId(Long orderId);



//    List<Review> findAllByStoreIdOrderByCreatedAtDesc(Long id);

//    List<Review> findAllByStarOrderByCreatedAtDesc(Integer star);

//    List<Review> findAllByStarOrderByCreatedAtDescAndByStarBetween(Integer star1, Integer star2);
}
