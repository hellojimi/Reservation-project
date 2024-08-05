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

    NOT_FOUND_RESTAURANT("등록된 매장이 없습니다."),

    FAILED_REGISTER_WAITING("웨이팅 등록에 실패하였습니다."),

    NOT_ALLOW_TODAY_BEFORE_DATE("입력한 날짜가 오늘 이전입니다."),
    NOT_ALLOW_NOW_BEFORE_TIME("현재 시간보다 이전 시간입니다."),

    FAILED_TO_RESERVE_BY_DUPLICATE("이미 예약된 내역이 존재합니다."),
    NOE_FOUND_RESERVATION_PERSON_INFO("예약자 정보가 존재하지 않습니다."),
    NOT_FOUND_RESERVATION_INFO("예약 내역이 존재하지 않습니다."),
    CANCELED_RESERVATION("취소된 예약 내역입니다.")
    ;

    private final String description;
}
