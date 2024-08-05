package zerobase.reservation.dto;

import lombok.Data;
import zerobase.reservation.domain.ReservationEntity;
import zerobase.reservation.type.ReservationStatus;

@Data
public class Reservation {

    private String reservationDate;
    private String reservationTime;

    public static ReservationEntity of(
            Long restaurantId, String accountId, Reservation reservation
    ) {
        return ReservationEntity.builder()
                .restaurantId(restaurantId)
                .customerId(accountId)
                .reservationStatus(ReservationStatus.WAITING)
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .build();
    }
}
