package zerobase.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    CONFIRM("예약 확정"),
    CANCEL("예약 취소")
    ;

    private final String description;
}
