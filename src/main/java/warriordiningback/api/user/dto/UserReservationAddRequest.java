package warriordiningback.api.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReservationAddRequest {

    private String userEmail;

    private Long placeId;

    private String reservationDate;

    private String reservationTime;

    private int count;

    private String orderNote;
}
