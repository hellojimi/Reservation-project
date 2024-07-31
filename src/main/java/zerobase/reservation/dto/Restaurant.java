package zerobase.reservation.dto;

import lombok.Data;
import zerobase.reservation.domain.RestaurantEntity;

import java.time.LocalDateTime;

@Data
public class Restaurant {

    String name;
    String address;
    String contents;
    String phone;
    String openTime;
    String lastOrderTime;

    public static RestaurantEntity of(Restaurant restaurant, String managerId) {
        return RestaurantEntity.builder()
                .managerId(managerId)
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .contents(restaurant.getContents())
                .phone(restaurant.getPhone())
                .openTime(restaurant.getOpenTime())
                .lastOrderTime(restaurant.getLastOrderTime())
                .regDt(LocalDateTime.now())
                .build();
    }

    public static RestaurantEntity updateOf(RestaurantEntity entity, Restaurant restaurant) {
        entity.setName(restaurant.getName());
        entity.setAddress(restaurant.getAddress());
        entity.setPhone(restaurant.getPhone());
        entity.setOpenTime(restaurant.getOpenTime());
        entity.setLastOrderTime(restaurant.getLastOrderTime());
        entity.setContents(restaurant.getContents());

        return entity;
    }

}
