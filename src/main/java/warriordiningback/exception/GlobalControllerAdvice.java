package warriordiningback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warriordiningback.exception.dto.DiningExceptionResponse;

import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<DiningExceptionResponse> responseMethodArgumentNotValidException(BindException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new DiningExceptionResponse(httpStatus, false, Objects.requireNonNull(exception.getFieldError()).getDefaultMessage()),
                httpStatus
        );
    }

    @ExceptionHandler(DiningApplicationException.class)
    public ResponseEntity<DiningExceptionResponse> responseApplicationException(DiningApplicationException exception) {
        DiningExceptionResponse errorResult = new DiningExceptionResponse(
                exception.getExceptionHttpStatus(), false, exception.getExceptionMessage());
        return new ResponseEntity<>(errorResult, exception.getExceptionHttpStatus());
    }
}
