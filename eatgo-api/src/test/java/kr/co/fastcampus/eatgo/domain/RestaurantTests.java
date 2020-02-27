package kr.co.fastcampus.eatgo.domain;

import static org.assertj.core.api.Assertions.*;

//import static org.hamcrest.MatcherAssert.*;
//import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

class RestaurantTests {

    @Test
    public void creation() {
        Restaurant restaurant = new Restaurant(1004L, "Bob Zip", "");
        assertThat(restaurant.getName()).isEqualTo("Bob Zip");
        assertThat(restaurant.getId()).isEqualTo(1004L);
    }

    @Test
    public void information() {
        Restaurant restaurant = new Restaurant(1004L, "Bob Zip", "Seoul");
        assertThat(restaurant.getInformation()).isEqualTo("Bob Zip in Seoul");
    }
}