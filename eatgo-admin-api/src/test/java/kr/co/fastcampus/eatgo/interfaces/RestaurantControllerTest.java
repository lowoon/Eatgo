package kr.co.fastcampus.eatgo.interfaces;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    private MockMvc mvc;

    @MockBean(RestaurantService.class)
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
            .id(1004L)
            .categoryId(1L)
            .name("Bob Zip")
            .address("Seoul")
            .build());

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"name\":\"Bob Zip\"")))
            .andExpect(content().string(containsString("\"id\":1004")));
    }

    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant = Restaurant.builder()
            .id(1004L)
            .categoryId(1L)
            .name("Bob Zip")
            .address("Seoul")
            .build();

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"id\":1004")))
            .andExpect(content().string(containsString("\"name\":\"Bob Zip\"")))
            .andExpect(content().string(containsString("\"address\":\"Seoul\"")));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(1234L))
            .willThrow(new RestaurantNotFoundException(1234L));

        mvc.perform(get("/restaurants/1234"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("{}"));
    }

    @Test
    public void createWithValidData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                .id(1234L)
                .categoryId(1L)
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .build();
        });

        mvc.perform(post("/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"categoryId\":1,\"name\":\"BeRyong\",\"address\":\"Busan\"}")
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("location", "/restaurants/1234"))
            .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"categoryId\":1,\"name\":\"\",\"address\":\"\"}")
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"categoryId\":1,\"name\":\"Joker\",\"address\":\"Busan\"}")
        )
            .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "Joker", "Busan");
    }

    @Test
    public void updateWithInvalidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"categoryId\":1,\"name\":\"\",\"address\":\"\"}")
        )
            .andExpect(status().isBadRequest());
    }
}