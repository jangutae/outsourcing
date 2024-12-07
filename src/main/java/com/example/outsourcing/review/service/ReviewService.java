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
import com.example.outsourcing.store.repository.StoreRepository;
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
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    // 리뷰 등록
    public ReviewResponseDto createReview(Long userId, Long orderId, ReviewRequestDto requestDto) {
        Order order = orderRepository.findOrderByIdOrElseThrow(orderId);
        Review review = new Review(requestDto.getStar(), requestDto.getContents());

        if (order.isBossAccessPossible(order)) {
            throw new CustomException(OrderErrorCode.NOT_ACCESS_BOSS);
        }

        if (!order.isAvailable(order)) {
            throw new CustomException(ReviewErrorCode.INVALID_OTHER_STATE);
        }

        reviewRepository.save(review);

        return ReviewResponseDto.toDto(review);
    }

    public List<ReviewResponseDto> readAllReviewByStoreId(Long storeId, Long userId, Double minStar, Double maxStar) {
        List<Review> allReview = reviewRepository.findAllReviewAndWithoutMineByStoreId(storeId, minStar, maxStar);

        allReview.removeIf(Review -> Review.getOrder().getUser().getId().equals(userId));
//        for (Review newReview : allReview) {
//            if (store.getId().equals(userId)) {
//                allReview.remove(newReview);
//            }
//        }
        return allReview.stream().map(ReviewResponseDto::toDto).toList();
    }

//        // 리뷰 조회 - 본인 작성한 리뷰는 제외
//        public List<ReviewResponseDto> readAllReview (Long storeId, Long userId){
//            List<Review> allByOrderId = reviewRepository.findAllByStoreIdOrderByCreatedAtDesc(storeId, userId);
//            return makeResponseDtos(allByOrderId);
//        }
//
//        //리뷰 조회 - 별점으로 조회 (본인 작성한 리뷰 제외하지 않음)
//        public List<ReviewResponseDto> readByStar (Long storeId, Long userId, Integer star1, Integer star2){
//            List<Review> allByStar = reviewRepository.findAllByStoreIdAndByStarBetween(storeId, userId, star1, star2);
//            return makeResponseDtos(allByStar);
//        }

        // 리스트 만드는 공통부분 메소드
//        public List<ReviewResponseDto> makeResponseDtos (List < Review > reviews) {
//            List<ReviewResponseDto> responseDtos = new ArrayList<>();
//            if (!reviews.isEmpty()) {
//                for (Review item : reviews) {
//                    ReviewResponseDto reviewResponseDto = new ReviewResponseDto(
//                            item.getId(),
//                            item.getStar(),
//                            item.getContents(),
//                            item.getOrder().getUser().getId(),
//                            item.getOrder().getId(),
//                            item.getOrder().getMenu().getMenuName(),
//                            item.getOrder().getStore().getStoreName(),
//                            item.getCreatedAt()
//                    );
//                    responseDtos.add(reviewResponseDto);
//                }
//            } else {
//                throw new CustomException(ReviewErrorCode.NOT_FOUND);
//            }
//            return responseDtos;
//        }



//    public List<ReviewResponseDto> readAllReviewByStoreId(Long storeId, Long userId, Double minStar, Double maxStar) {
//        List<Review> allReview = reviewRepository.findAllReviewAndWithoutMineByStoreId(storeId, userId, minStar, maxStar);
//
//        allReview.removeIf(Review -> Review.getOrder().getUser().getId().equals(userId));
////        for (Review newReview : allReview) {
////            if (store.getId().equals(userId)) {
////                allReview.remove(newReview);
////            }
////        }
//        return allReview.stream().map(ReviewResponseDto::toDto).toList();
//    }
}
