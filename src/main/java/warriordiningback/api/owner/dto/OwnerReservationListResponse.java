package warriordiningback.api.owner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import warriordiningback.domain.reservation.Reservation;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerReservationListResponse {

    private Long id;

    private String reservationDate;

    private String reservationStatus;

    private Long placeId;

    private String placeName;

    private String userName;

    private int count;

    private String reservationTime;

    private String orderNote;

    public OwnerReservationListResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.reservationDate = formatReservationDate(reservation.getReservationDate());
        this.reservationStatus = reservation.getCode().getValue();
        this.placeId = reservation.getPlace().getId();
        this.placeName = reservation.getPlace().getName();
        this.userName = reservation.getUser().getName();
        this.count = reservation.getCount();
        this.reservationTime = formatReservationTime(reservation.getReservationTime());
        this.orderNote = reservation.getOrderNote();
    }

    private String formatReservationTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    private String formatReservationDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

}
