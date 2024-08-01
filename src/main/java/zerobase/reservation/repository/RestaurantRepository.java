package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservation.domain.RestaurantEntity;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    // 관리자가 등록한 모든 매장
    List<RestaurantEntity> findAllByManagerId(String managerId);
}
