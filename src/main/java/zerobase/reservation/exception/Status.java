package zerobase.reservation.exception;

import lombok.Getter;
import zerobase.reservation.type.ErrorCode;

@Getter
public final class Status extends RuntimeException {
    private final ErrorCode errorCode;
    private final String errorMessage;

    public Status(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
