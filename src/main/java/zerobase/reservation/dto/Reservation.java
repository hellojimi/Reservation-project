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
                .accountId(accountId)
                .reservationStatus(ReservationStatus.CONFIRM)
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .build();
    }
}
