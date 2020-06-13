package kr.co.fastcampus.eatgo.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/restaurants/{restaurantId}/reviews")
    public List<Review> list(@PathVariable("restaurantId") Long restaurantId) {
        return reviewService.getReviews(restaurantId);
    }
}
