package com.example.outsourcing.review.controller;

import com.example.outsourcing.review.dto.ReviewRequestDto;
import com.example.outsourcing.review.dto.ReviewResponseDto;
import com.example.outsourcing.review.service.ReviewService;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Lint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping("/{orderId}")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long orderId,
            @RequestBody ReviewRequestDto requestDto,
            @SessionAttribute(value = "id") User user
    ) {
        ReviewResponseDto reviewResponseDto = reviewService.createReview(user.getId(), orderId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<List<ReviewResponseDto>> readAllReviewByStoreId(
            @PathVariable Long storeId,
            @SessionAttribute(value = "id") User user,
            @RequestParam(name = "minStar") Double minStar,
            @RequestParam(name = "maxStar") Double maxStar
    ) {
        List<ReviewResponseDto> allReviewByStoreId = reviewService.readAllReviewByStoreId(user.getId(), storeId, minStar, maxStar);

        return ResponseEntity.ok().body(allReviewByStoreId);
    }

//    @GetMapping("/{storeId}")
//    public ResponseEntity<List<ReviewResponseDto>> readAllReview(
//            @PathVariable Long storeId,
//            @RequestParam(required = false, value = "star") List<Integer> star,
//            @SessionAttribute(value = "id") User user
//    ) {
//        List<ReviewResponseDto> responseDtos = new ArrayList<>();
//
//        if (star != null) {
//            // 파라미터 값이(별점이) 있을 경우
//            responseDtos = reviewService.readByStar(storeId, userId, star.get(0), star.get(1));
//        } else {
//            //파라미터 값이 없을 경우 (별점없이 가게로만 조회할 때)
//            responseDtos = reviewService.readAllReview(storeId, userId);
//        }
//        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
//    }

}
