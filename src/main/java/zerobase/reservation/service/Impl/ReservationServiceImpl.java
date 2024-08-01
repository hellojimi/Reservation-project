package zerobase.reservation.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerobase.reservation.domain.ReservationEntity;
import zerobase.reservation.dto.Reservation;
import zerobase.reservation.repository.ReservationRepository;
import zerobase.reservation.service.ReservationService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public ReservationEntity registerWaiting(
            Long restaurantId, String accountId, Reservation reservation
    ) {
        return reservationRepository.save(Reservation.of(restaurantId, accountId, reservation));
    }
}
