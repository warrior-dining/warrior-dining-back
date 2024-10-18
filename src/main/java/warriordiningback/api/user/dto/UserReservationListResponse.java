package warriordiningback.api.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import warriordiningback.domain.reservation.Reservation;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReservationListResponse {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reservationDate;

    private String reservationStatus;

    private Long placeId;

    private String placeName;

    private int count;
    
    private boolean reviewExists;

    @JsonFormat(pattern = "HH:mm:ss")
    private Date registrationTime;

    private String orderNote;

    private Boolean bookMark;

    public UserReservationListResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.reservationDate = reservation.getReservationDate();
        this.reservationStatus = reservation.getCode().getValue();
        this.placeId = reservation.getPlace().getId();
        this.placeName = reservation.getPlace().getName();
        this.count = reservation.getCount();
        this.registrationTime = reservation.getReservationTime();
        this.orderNote = reservation.getOrderNote();
        this.bookMark = reservation.getUser().getBookmarks().stream()
                .anyMatch(place -> place.getId().equals(reservation.getPlace().getId()));
        this.reviewExists = false;
    }

}
