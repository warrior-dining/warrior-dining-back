package warriordiningback.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReservationInfoResponse {

    private Long reservationId;

    private String placeName ;

    private String startTime ;

    private String endTime ;

    private String reservationDate;

    private String reservationTime;

    private int count;

    private String orderNote;

}
