package zerobase.reservation.service;

import zerobase.reservation.domain.ReservationEntity;
import zerobase.reservation.dto.ReservationList;
import zerobase.reservation.dto.Reservation;

import java.util.List;

public interface ReservationService {

    // 웨이팅 등록하기
    ReservationEntity registerWaiting(Long restaurantId, String accountId, Reservation reservation);

    // 현장 예약 확인
    ReservationEntity checkReservationUsingKiosk(Long restaurantId, String phone);

    List<ReservationList> getMyReservationList(String accountId);

    Object getManagerReservationList(String managerId);

    // 예약 승인 여부
    void getReservationApproveResult(Long reservationId, String status);
}
