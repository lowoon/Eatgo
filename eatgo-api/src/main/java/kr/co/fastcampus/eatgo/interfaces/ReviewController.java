package kr.co.fastcampus.eatgo.interfaces;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(@PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review resource) throws URISyntaxException {
        Review review = reviewService.addReview(restaurantId, resource);
        String url = "/restaurants/" + restaurantId + "/reviews/" + review.getId();
        URI location = new URI(url);
        return ResponseEntity.created(location)
                .body("{}");
    }
}
