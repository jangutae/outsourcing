package com.example.outsourcing.review.service;

import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.OrderErrorCode;
import com.example.outsourcing.common.exception.ReviewErrorCode;
import com.example.outsourcing.order.entity.Order;
import com.example.outsourcing.order.repository.OrderRepository;
import com.example.outsourcing.review.dto.ReviewRequestDto;
import com.example.outsourcing.review.dto.ReviewResponseDto;
import com.example.outsourcing.review.entity.Review;
import com.example.outsourcing.review.repository.ReviewRepository;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // 리뷰 등록
    public ReviewResponseDto createReview(Long userId, Long orderId, ReviewRequestDto requestDto) {
        User user = userRepository.findByIdOrElseThrows(userId);
        Order order = orderRepository.findOrderByIdOrElseThrow(orderId);
        Review review = new Review(user, order.getStore(), order, requestDto.getStar(), requestDto.getContents());

        if (order.isBossAccessPossible(user)) {
            throw new CustomException(OrderErrorCode.NOT_ACCESS_BOSS);
        }

        if (!order.isAvailable(order)) {
            throw new CustomException(ReviewErrorCode.INVALID_OTHER_STATE);
        }

        reviewRepository.save(review);

        return ReviewResponseDto.toDto(review);
    }

    public List<ReviewResponseDto> readAllReviewByStoreId(Long userId, Long storeId, Integer minStar, Integer maxStar) {
        List<Review> allReview = reviewRepository.findAllReviewAndWithoutMineByStoreId(storeId, minStar, maxStar);

        allReview.removeIf(Review -> Review.getUser().getId().equals(userId));

        return allReview.stream().map(ReviewResponseDto::toDto).toList();
    }
}
