package zerobase.reservation.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerobase.reservation.domain.RestaurantEntity;
import zerobase.reservation.dto.Restaurant;
import zerobase.reservation.exception.UserException;
import zerobase.reservation.repository.RestaurantRepository;
import zerobase.reservation.service.RestaurantService;
import zerobase.reservation.type.ErrorCode;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantEntity register(String managerId, Restaurant restaurant) {
        return restaurantRepository.save(
                Restaurant.of(restaurant, managerId)
        );
    }

    @Override
    public List<RestaurantEntity> registeredRestaurantList(String managerId) {
        List<RestaurantEntity> listByManagerId = new ArrayList<>();

        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAllByManagerId(managerId);
        for (RestaurantEntity restaurant : restaurantEntities) {
            listByManagerId.add(restaurant);
        }

        return listByManagerId;
    }

    @Override
    public List<RestaurantEntity> restaurantList() {
        return restaurantRepository.findAll();
    }

    @Override // findAllById
    public RestaurantEntity findRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_RESTAURANT));
    }

    @Override
    public RestaurantEntity update(Long restaurantId, Restaurant restaurant) {
        RestaurantEntity result = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_RESTAURANT));

        restaurantRepository.save(Restaurant.updateOf(result, restaurant));
        return result;
    }

    @Override
    public void delete(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
