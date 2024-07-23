package zerobase.reservation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zerobase.reservation.dto.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ErrorResponse handlerException(UserException e) {
        log.error("{} is occurred.", e.getErrorCode());

        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }
}
