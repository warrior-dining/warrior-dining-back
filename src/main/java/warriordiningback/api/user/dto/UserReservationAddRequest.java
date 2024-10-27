package warriordiningback.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReservationAddRequest {

    @NotBlank
    private String userEmail;
    @Positive
    private Long placeId;
    @NotBlank
    private String reservationDate;
    @NotBlank
    private String reservationTime;
    @Positive
    private int count;

    private String orderNote;


}
