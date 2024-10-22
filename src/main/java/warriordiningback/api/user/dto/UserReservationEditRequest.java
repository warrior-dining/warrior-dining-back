package warriordiningback.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReservationEditRequest {

    private String reservationDate;

    private String reservationTime;

    private int count;

    private String orderNote;
}
