package zerobase.reservation.dto;

import lombok.Builder;
import lombok.Data;
import zerobase.reservation.domain.ReservationEntity;
import zerobase.reservation.domain.RestaurantEntity;
import zerobase.reservation.domain.AccountEntity;
import zerobase.reservation.type.ReservationStatus;

@Data
@Builder
public class ReservationList {

    private Long restaurantId;                      // 매장 아이디
    private String restaurantName;                  // 상호명

    private String customerId;                       // 예약자 이메일
    private String customerName;                     // 예약자 이름
    private String phone;                            // 예약자 전화번호

    private ReservationStatus reservationStatus;    // 예약 상태
    private String reservationDate;                 // 예약 일
    private String reservationTime;                 // 예약 시간

    public static ReservationList of(
            ReservationEntity reservation,
            AccountEntity account,
            RestaurantEntity restaurant
    ) {
        return ReservationList.builder()
                .restaurantId(restaurant.getId())
                .restaurantName(restaurant.getName())
                .customerId(account.getId())
                .customerName(account.getName())
                .phone(account.getPhone())
                .reservationStatus(reservation.getReservationStatus())
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .build();
    }
}
