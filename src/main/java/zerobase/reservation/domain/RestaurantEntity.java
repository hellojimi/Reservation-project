package zerobase.reservation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "RESTAURANT")
public class RestaurantEntity {

    @Id
    @Column(name = "restaurant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String managerId;   // 관리자 아이디
    String name;        // 상호명
    String address;     // 매장 주소
    String phone;       // 전화번호
    String openTime;    // 오픈 시간
    String lastOrderTime;   // 마지막 주문 시간

    @Lob
    String contents;    // 매장 정보

    LocalDateTime regDt; //등록일
    LocalDateTime udtDt; //수정일
}
