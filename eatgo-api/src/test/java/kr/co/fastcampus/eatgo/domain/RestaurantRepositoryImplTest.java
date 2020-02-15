package kr.co.fastcampus.eatgo.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RestaurantRepositoryImplTest {
    @Test
    void save() {
        RestaurantRepository restaurantRepository = new RestaurantRepositoryImpl();
        int oldCount = restaurantRepository.findAll().size();

        Restaurant restaurant = new Restaurant("BeRyong", "Busan");

        Restaurant saved = restaurantRepository.save(restaurant);

        assertThat(restaurantRepository.findAll().size()).isEqualTo(oldCount + 1);
        assertThat(saved.getId()).isEqualTo(1234L);
    }
}