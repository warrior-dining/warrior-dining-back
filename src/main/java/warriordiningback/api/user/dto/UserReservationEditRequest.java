package warriordiningback.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReservationEditRequest {

    @NotBlank
    private String reservationDate;
    @NotBlank
    private String reservationTime;
    @Positive
    private int count;

    private String orderNote;
}
