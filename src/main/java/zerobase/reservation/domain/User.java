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
@Entity
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    private String name;
    private String phone;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserAuth userType;          // 회원타입

    private LocalDateTime registerAt;   // 가입일자

    private boolean emailAuthYn;        // 이메일 인증 유무
    private String emailAuthKey;        // 이메일 인증 키
    private LocalDateTime emailAuthDt;  // 이메일 인증 날짜

}
