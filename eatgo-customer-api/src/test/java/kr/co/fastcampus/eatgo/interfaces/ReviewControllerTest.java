package kr.co.fastcampus.eatgo.interfaces;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void createWithValidAttributes() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsInVzZXJOYW1lIjoidGVzdGVyIn0.bUqxLMPHoRhKqHFqOJp9BkbLn7Ym48k2b2XL2tPUSKU";

        when(reviewService.addReview(1L, "tester", 3, "JMT")).thenReturn(
            Review.builder()
                .id(123L)
                .build()
        );

        mvc.perform(post("/restaurants/1/reviews")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"score\":3,\"description\":\"JMT\"}"))
            .andExpect(status().isCreated())
            .andExpect(header().string("location", "/restaurants/1/reviews/123"));

        verify(reviewService).addReview(1L, "tester", 3, "JMT");
    }

    @Test
    public void createWithInvalidAttributes() throws Exception {
        mvc.perform(post("/restaurants/1/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isBadRequest());

        verify(reviewService, never()).addReview(any(), any(), any(), any());
    }
}