package com.example.outsourcing.review.service;

import com.example.outsourcing.common.constants.AccountRole;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.ReviewErrorCode;
import com.example.outsourcing.order.entity.Order;
import com.example.outsourcing.order.enums.DeliveryState;
import com.example.outsourcing.order.repository.OrderRepository;
import com.example.outsourcing.review.dto.ReviewResponseDto;
import com.example.outsourcing.review.entity.Review;
import com.example.outsourcing.review.repository.ReviewRepository;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.repository.UserRepository;
import com.example.outsourcing.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    // 리뷰 등록
    public ReviewResponseDto createReview(Long userId, Long orderId, Integer star, String contents) {

        log.info("userID : {} ", userId);

        Order orderById = orderRepository.findOrderByIdOrElseThrow(orderId);
        User userById = userRepository.findByIdOrElseThrows(userId);
        Review byOrderId = reviewRepository.findByOrderId(orderId);

        if (userById.getId() == orderById.getUser().getId()
                && orderById.getState() == Order.DeliveryState.DELIVERY_COMPLETE
        ) {

            if (userById.getRole() == AccountRole.USER) {
                Review review = new Review(star, contents, orderById);
                Review savedReview = reviewRepository.save(review);
                return new ReviewResponseDto(
                        savedReview.getId(),
                        savedReview.getStar(),
                        savedReview.getContents(),
                        savedReview.getOrder().getUser().getId(),
                        savedReview.getOrder().getId(),
                        savedReview.getOrder().getStore().getStoreName(),
                        savedReview.getOrder().getMenu().getMenuName(),
                        savedReview.getCreatedAt());
            } else {
                throw new CustomException(ReviewErrorCode.INVALID_OWNER);
            }
        } else {
            throw new CustomException(ReviewErrorCode.INVALID_NOT_ME);
        }
    }

    // 리뷰 조회 - 본인 작성한 리뷰는 제외
    public List<ReviewResponseDto> readAllReview(Long storeId, Long userId) {
        List<Review> allByOrderId = reviewRepository.findAllByStoreIdOrderByCreatedAtDesc(storeId, userId);
        return makeResponseDtos(allByOrderId);
    }

    //리뷰 조회 - 별점으로 조회 (본인 작성한 리뷰 제외하지 않음)
    public List<ReviewResponseDto> readByStar(Long storeId, Long userId, Integer star1, Integer star2) {
        List<Review> allByStar = reviewRepository.findAllByStoreIdAndByStarBetween(storeId, userId, star1, star2);
        return makeResponseDtos(allByStar);
    }

    // 리스트 만드는 공통부분 메소드
    public List<ReviewResponseDto> makeResponseDtos(List<Review> reviews) {
        List<ReviewResponseDto> responseDtos = new ArrayList<>();
        if (!reviews.isEmpty()) {
            for (Review item : reviews) {
                ReviewResponseDto reviewResponseDto = new ReviewResponseDto(
                        item.getId(),
                        item.getStar(),
                        item.getContents(),
                        item.getOrder().getUser().getId(),
                        item.getOrder().getId(),
                        item.getOrder().getMenu().getMenuName(),
                        item.getOrder().getStore().getStoreName(),
                        item.getCreatedAt()
                );
                responseDtos.add(reviewResponseDto);
            }
        } else {
            throw new CustomException(ReviewErrorCode.NOT_FOUND);
        }
        return responseDtos;
    }

}
