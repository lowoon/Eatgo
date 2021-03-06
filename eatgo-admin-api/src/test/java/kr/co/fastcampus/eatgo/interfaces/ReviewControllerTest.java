package kr.co.fastcampus.eatgo.interfaces;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void list() throws Exception {
        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(Review.builder()
                .name("Joker")
                .score(4)
                .description("JMT")
                .build());

        when(reviewService.getReviews(1004L)).thenReturn(mockReviews);

        mvc.perform(get("/restaurants/1004/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"Joker\"")))
                .andExpect(content().string(containsString("\"score\":4")))
                .andExpect(content().string(containsString("\"description\":\"JMT\"")));
    }
}