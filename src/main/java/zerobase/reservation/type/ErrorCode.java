package zerobase.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_USER("이미 존재하는 회원입니다."),

    FAILED_SEND_MAIL("이메일 전송을 실패하였습니다."),
    UNKNOWN_AUTH_KEY("유효하지 않은 인증키입니다.")
    ;

    private final String description;
}
