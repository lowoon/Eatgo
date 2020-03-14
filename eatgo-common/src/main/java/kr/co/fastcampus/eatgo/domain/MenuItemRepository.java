package kr.co.fastcampus.eatgo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {

    List<MenuItem> findAllByRestaurantId(Long restaurantId);

    void deleteById(Long id);
}
