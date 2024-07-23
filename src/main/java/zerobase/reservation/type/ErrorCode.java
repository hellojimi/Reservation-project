package zerobase.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_USER("이미 존재하는 회원입니다.")
    ;

    private final String description;
}
