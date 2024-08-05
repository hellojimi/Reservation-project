package zerobase.reservation.domain;

import jakarta.persistence.*;
import lombok.*;
import zerobase.reservation.type.ReservationStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "RESERVATION")
public class ReservationEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private Long restaurantId;                        // 매장 아이디
    private String customerId;                        // 예약자 아이디

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;      // 예약 상태

    private String reservationDate;                   // 예약 날짜
    private String reservationTime;                   // 예약 시간
}
