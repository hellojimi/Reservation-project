package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zerobase.reservation.domain.ReservationEntity;
import zerobase.reservation.domain.UserEntity;

import java.util.Optional;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    // 예약자 아이디와 예약 일자로 중복 예약 여부 확인
    boolean existsByCustomerIdAndReservationDateAndReservationTime(
            String accountId, String reservationDate, String reservationTime
    );

    // 오늘 날짜 예약 내역 조회
    // 여러 예약 건이 있는 경우 가장 빠른 시간 1개만 반환
    Optional<ReservationEntity> findTop1ByRestaurantIdAndCustomerIdAndReservationDateOrderByReservationTime(
            Long restaurantId, String accountId, String reservationDate
    );

    // 예약자 아이디에 해당하는 전체 예약 내역
    List<ReservationEntity> findAllByCustomerId(String accountId);

    // 매장 아이디에 해당하는 매장 예약 내역
    List<ReservationEntity> findAllByRestaurantId(Long restaurantId);
}
