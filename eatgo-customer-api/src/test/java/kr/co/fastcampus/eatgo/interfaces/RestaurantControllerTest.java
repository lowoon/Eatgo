package kr.co.fastcampus.eatgo.interfaces;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.exception.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean(RestaurantService.class)
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
            .id(1004L)
            .categoryId(1L)
            .name("Bob Zip")
            .address("Seoul")
            .build());

        when(restaurantService.getRestaurants("Seoul", 1L)).thenReturn(restaurants);

        mvc.perform(get("/restaurants?region=Seoul&category=1"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"name\":\"Bob Zip\"")))
            .andExpect(content().string(containsString("\"id\":1004")));
    }

    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant = Restaurant.builder()
            .id(1004L)
            .name("Bob Zip")
            .address("Seoul")
            .build();

        MenuItem menuItem = MenuItem.builder()
            .name("Kimchi")
            .build();

        Review review = Review.builder()
            .name("Joker")
            .score(3)
            .description("JMT")
            .build();

        restaurant.setMenuItems(Collections.singletonList(menuItem));
        restaurant.setReviews(Collections.singletonList(review));

        when(restaurantService.getRestaurant(1004L)).thenReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"id\":1004")))
            .andExpect(content().string(containsString("\"name\":\"Bob Zip\"")))
            .andExpect(content().string(containsString("\"address\":\"Seoul\"")))
            .andExpect(content().string(containsString("Kimchi")))
            .andExpect(content().string(containsString("\"name\":\"Joker\"")))
            .andExpect(content().string(containsString("\"score\":3")));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        when(restaurantService.getRestaurant(1234L))
            .thenThrow(new RestaurantNotFoundException(1234L));

        mvc.perform(get("/restaurants/1234"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("{}"));
    }
}