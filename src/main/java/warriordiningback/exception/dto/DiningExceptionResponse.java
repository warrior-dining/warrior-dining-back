package warriordiningback.exception.dto;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class DiningExceptionResponse {

    private final int code;
    private final boolean status;
    private final String message;

    public DiningExceptionResponse(HttpStatusCode httpStatus, boolean status, String message) {
        this.code = httpStatus.value();
        this.status = status;
        this.message = message;
    }
}
