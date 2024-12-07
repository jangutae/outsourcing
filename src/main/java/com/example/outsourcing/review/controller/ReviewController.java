package com.example.outsourcing.review.controller;

import com.example.outsourcing.review.dto.ReviewRequestDto;
import com.example.outsourcing.review.dto.ReviewResponseDto;
import com.example.outsourcing.review.service.ReviewService;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @SessionAttribute(value = "id") User user,
            @PathVariable Long storeId,
            @RequestParam(name = "minStar") Integer minStar,
            @RequestParam(name = "maxStar") Integer maxStar
    ) {
        List<ReviewResponseDto> allReviewByStoreId = reviewService.readAllReviewByStoreId(user.getId(), storeId,  minStar, maxStar);

        return ResponseEntity.ok().body(allReviewByStoreId);
    }
}
