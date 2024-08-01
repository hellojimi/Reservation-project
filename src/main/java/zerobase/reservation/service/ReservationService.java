package zerobase.reservation.service;

import zerobase.reservation.domain.ReservationEntity;
import zerobase.reservation.dto.Reservation;

public interface ReservationService {

    ReservationEntity registerWaiting(Long restaurantId, String accountId, Reservation reservation);
}
