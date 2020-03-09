package kr.co.fastcampus.eatgo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findAllByRestaurantId(Long restaurantId);

    Review save(Review review);
}
