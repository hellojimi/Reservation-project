package zerobase.reservation.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerobase.reservation.domain.RestaurantEntity;
import zerobase.reservation.dto.Restaurant;
import zerobase.reservation.exception.Status;
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

    // 매장 등록
    @Override
    public RestaurantEntity register(String managerId, Restaurant restaurant) {
        return restaurantRepository.save(
                Restaurant.of(restaurant, managerId)
        );
    }

    // 해당 관리자가 등록한 매장 목록
    @Override
    public List<RestaurantEntity> registeredRestaurantList(String managerId) {
        List<RestaurantEntity> listByManagerId = new ArrayList<>();

        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAllByManagerId(managerId);
        for (RestaurantEntity restaurant : restaurantEntities) {
            listByManagerId.add(restaurant);
        }

        return listByManagerId;
    }

    // 전체 매장 목록
    @Override
    public List<RestaurantEntity> restaurantList() {
        return restaurantRepository.findAll();
    }

    // 매장 정보 찾기
    @Override
    public RestaurantEntity findRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new Status(ErrorCode.NOT_FOUND_RESTAURANT));
    }

    // 매장 정보 수정
    @Override
    public RestaurantEntity update(Long restaurantId, Restaurant restaurant) {
        RestaurantEntity result = findRestaurant(restaurantId);
        restaurantRepository.save(Restaurant.updateOf(result, restaurant));

        return result;
    }

    // 매장 삭제
    @Override
    public void delete(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
