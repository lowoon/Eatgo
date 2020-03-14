package kr.co.fastcampus.eatgo.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.ReviewRepository;

class ReviewServiceTest {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    void getReviews() {
        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(Review.builder()
                .name("Joker")
                .score(4)
                .description("JMT")
                .build());

        given(reviewRepository.findAll()).willReturn(mockReviews);
        Review review = mockReviews.get(0);

        assertThat(review.getDescription()).isEqualTo("JMT");
    }
}