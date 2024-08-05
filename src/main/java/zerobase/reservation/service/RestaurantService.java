package zerobase.reservation.service;

import zerobase.reservation.domain.RestaurantEntity;
import zerobase.reservation.dto.Restaurant;

import java.util.List;

public interface RestaurantService {

    // 관리자 매장 등록
    RestaurantEntity register(String managerId, Restaurant restaurant);

    // 등록한 매장 목록
    List<RestaurantEntity> registeredRestaurantList(String managerId);

    // 전체 매장 목록
    List<RestaurantEntity> restaurantList();

    RestaurantEntity findRestaurant(Long restaurantId);

    // 매장 정보 수정
    RestaurantEntity update(Long restaurantId, Restaurant restaurant);

    void delete(Long restaurantId);

    // 매장 검색

}
