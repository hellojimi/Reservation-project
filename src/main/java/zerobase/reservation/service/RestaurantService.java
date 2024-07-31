package zerobase.reservation.service;

import zerobase.reservation.domain.RestaurantEntity;
import zerobase.reservation.dto.Restaurant;

import java.util.List;

public interface RestaurantService {

    RestaurantEntity register(String managerId, Restaurant restaurant);

    List<RestaurantEntity> registeredRestaurantList(String managerId);

    List<RestaurantEntity> restaurantList();

    RestaurantEntity findRestaurant(Long restaurantId);

    RestaurantEntity update(Long restaurantId, Restaurant restaurant);

    void delete(Long restaurantId);

}
