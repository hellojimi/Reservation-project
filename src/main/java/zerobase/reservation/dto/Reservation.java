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

    public static ReservationEntity updateConfirm(ReservationEntity reservation) {
        ReservationEntity entity = new ReservationEntity();
        entity.setId(reservation.getId());
        entity.setCustomerId(reservation.getCustomerId());
        entity.setReservationStatus(ReservationStatus.CONFIRM);
        entity.setReservationDate(reservation.getReservationDate());
        entity.setReservationTime(reservation.getReservationTime());

        return entity;
    }

    public static ReservationEntity updateCancel(ReservationEntity reservation) {
        ReservationEntity entity = new ReservationEntity();
        entity.setId(reservation.getId());
        entity.setCustomerId(reservation.getCustomerId());
        entity.setReservationStatus(ReservationStatus.CANCEL);
        entity.setReservationDate(reservation.getReservationDate());
        entity.setReservationTime(reservation.getReservationTime());

        return entity;
    }
}
