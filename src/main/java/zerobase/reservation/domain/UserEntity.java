package zerobase.reservation.domain;

import jakarta.persistence.*;
import lombok.*;
import zerobase.reservation.type.UserAuth;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "ACCOUNT")
public class UserEntity {

    @Id
    @Column(name = "account_id")
    private String id;                  // 이메일

    private String name;                // 이름
    private String phone;               // 전화번호
    private String password;            // 비밀번호

    @Enumerated(EnumType.STRING)
    private UserAuth userType;          // 회원타입

    private LocalDateTime registerAt;   // 가입일자

    private boolean emailAuthYn;        // 이메일 인증 유무
    private String emailAuthKey;        // 이메일 인증 키
    private LocalDateTime emailAuthDt;  // 이메일 인증 날짜

}
