package kr.co.fastcampus.eatgo.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RestaurantTest {

    @Test
    public void creation() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob Zip")
                .address("Seoul")
                .build();

        assertThat(restaurant.getName()).isEqualTo("Bob Zip");
        assertThat(restaurant.getId()).isEqualTo(1004L);
    }

    @Test
    public void information() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob Zip")
                .address("Seoul")
                .build();

        assertThat(restaurant.getInformation()).isEqualTo("Bob Zip in Seoul");
    }
}