package zerobase.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_USER("이미 존재하는 회원입니다."),
    NOT_FOUND_USER("존재하지 않는 회원입니다."),

    FAILED_SEND_MAIL("이메일 전송을 실패하였습니다."),
    UNKNOWN_AUTH_KEY("유효하지 않은 인증키입니다."),

    REQUIRED_LOGIN("로그인이 필요한 서비스입니다."),
    UN_MATCHED_PASSWORD("비밀번호가 일치하지 않습니다."),

    NOT_FOUND_RESTAURANT("등록된 매장이 없습니다.")
    ;

    private final String description;
}
