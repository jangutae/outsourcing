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
    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    // 리뷰 등록
    public ReviewResponseDto createReview(Long userId, Long orderId, Integer star, String contents) {

        Order orderById = orderRepository.findOrderByIdOrElseThrow(orderId);
        User userById = userRepository.findByIdOrElseThrows(userId);

        if (userById.getId() == orderById.getUser().getId()
                && orderById.getState() == DeliveryState.ORDER_COMPLETE
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
                        savedReview.getOrder().getMenu().getMenuName(),
                        savedReview.getCreatedAt()
                );
            } else {
                throw new CustomException(ReviewErrorCode.INVALID_OWNER);
            }
        } else {
            throw new CustomException(ReviewErrorCode.INVALID_NOT_ME);
        }

    }

    // 리뷰 조회 - 본인 작성한 리뷰는 제외
    public List<ReviewResponseDto> readAllReview(Long userId, Long orderId) {
//        Order orderByIdOrElseThrow = orderRepository.findOrderByIdOrElseThrow(orderId);


        Long orderUser = reviewRepository.findByOrderId(orderId).getUserId();


        List<Review> allByOrderId = reviewRepository.findAllByOrderIdOrderByCreatedAtDesc(orderId);
        List<ReviewResponseDto> responseDtos = new ArrayList<>();
        for (Review item : allByOrderId) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(
                    item.getId(),
                    item.getStar(),
                    item.getContents(),
                    item.getOrder().getUser().getId(),
                    item.getOrder().getId(),
                    item.getOrder().getMenu().getMenuName(),
                    item.getCreatedAt()
            );
            responseDtos.add(reviewResponseDto);
        }

        return responseDtos;
    }


//    public ReviewResponseDto createReview(Long id, Integer star, String contents, Long userId, Long menuId, String state, Long storeId, String menuName) {
//
//        User userById = userRepository.findByIdOrElseThrows(id);
//        if (userById.getRole() == AccountRole.USER) {
//            Review review = new Review(star, contents, userId, menuId, state, storeId, menuName);
//            Review savedReview = reviewRepository.save(review);
//
//            return new ReviewResponseDto(
//                    savedReview.getId(),
//                    savedReview.getStar(),
//                    savedReview.getContents(),
//                    savedReview.getUserId(),
//                    savedReview.getStoreId(),
//                    savedReview.getMenuName(),
//                    savedReview.getCreatedAt()
//            );
//
//        } else {
//            throw new CustomException(ReviewErrorCode.INVALID_OWNER);
////            return new ReviewResponseDto(100L,100,"없다",id, 100L,"메뉴없다");
//        }
//
//    }

//    public List<ReviewResponseDto> reviewsByStore(Long id) {
//        List<Review> allByStoreId = reviewRepository.findAllByStoreIdOrderByCreatedAtDesc(id);
//        List<ReviewResponseDto> responseDtos = new ArrayList<>();
//        if (!allByStoreId.isEmpty()) {
//            for (Review item : allByStoreId) {
//                ReviewResponseDto reviewResponse = new ReviewResponseDto(
//                        item.getId(),
//                        item.getStar(),
//                        item.getContents(),
//                        item.getUserId(),
//                        item.getStoreId(),
//                        item.getMenuName(),
//                        item.getCreatedAt()
//                );
//                responseDtos.add(reviewResponse);
//            }
//            return responseDtos;
//        } else {
//            throw new CustomException(ReviewErrorCode.NOT_FOUND);
//        }
//
//    }

//    public List<ReviewResponseDto> reviewsByStar(Long storeId, List<Integer> star) {
//
//        log.info("star.get(0) : {}",star.get(0));
//        log.info("star.get(0) : {}",star.get(1));
//        List<Review> allByStar = reviewRepository.findAllByStarOrderByCreatedAtDescAndByStarBetween(star.get(0), star.get(1));
//        List<ReviewResponseDto> responseDtos = new ArrayList<>();
//        for (Review item : allByStar) {
//            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(
//                    item.getId(),
//                    item.getStar(),
//                    item.getContents(),
//                    item.getUserId(),
//                    item.getStoreId(),
//                    item.getMenuName(),
//                    item.getCreatedAt()
//            );
//            responseDtos.add(reviewResponseDto);
//        }
//        return responseDtos;
//
//        return null;
//    }

//    public List<ReviewResponseDto> reviewsByStar(Long storeId, List<Integer> starValue) {
//        List<Review> allByStar = reviewRepository.findAllByStarOrderByCreatedAtDesc(starValue);
//        List<ReviewResponseDto> responseDtos = new ArrayList<>();
//        for(Review item : allByStar){
//            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(
//                    item.getId(),
//                    item.getStar(),
//                    item.getContents(),
//                    item.getUserId(),
//                    item.getStoreId(),
//                    item.getMenuName(),
//                    item.getCreatedAt()
//            );
//            responseDtos.add(reviewResponseDto);
//        }
//        return responseDtos;
//    }
}
