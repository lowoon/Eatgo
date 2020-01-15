package kr.co.fastcampus.eatgo.domain;

import static org.assertj.core.api.Assertions.*;

//import static org.hamcrest.MatcherAssert.*;
//import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

class RestaurantTests {
	@Test
	public void creation() {
		Restaurant restaurant = new Restaurant("Bob Zip", "");
		//assertThat(restaurant.getName(), is("Bob Zip"));
		assertThat(restaurant.getName()).isEqualTo("Bob Zip");
	}

	@Test
	public void information() {
		Restaurant restaurant = new Restaurant("Bob Zip", "Seoul");
		//assertThat(restaurant.getInformation(), is("Bob Zip in Seoul"));
		assertThat(restaurant.getName()).isEqualTo("Bob Zip in Seoul");
	}
}