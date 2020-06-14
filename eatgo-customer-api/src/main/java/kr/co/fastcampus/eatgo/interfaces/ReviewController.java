package kr.co.fastcampus.eatgo.interfaces;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<Void> create(
        Authentication authentication,
        @PathVariable("restaurantId") Long restaurantId,
        @Valid @RequestBody Review resource
    ) throws URISyntaxException {
        Claims claims = (Claims)authentication.getPrincipal();
        String name = claims.get("userName", String.class);
        int score = resource.getScore();
        String description = resource.getDescription();

        Review review = reviewService.addReview(restaurantId, name, score, description);
        String url = "/restaurants/" + restaurantId + "/reviews/" + review.getId();
        URI location = new URI(url);
        return ResponseEntity.created(location).build();
    }
}
