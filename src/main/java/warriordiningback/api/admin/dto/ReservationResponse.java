package warriordiningback.api.admin.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import warriordiningback.domain.reservation.Reservation;

@Data
@NoArgsConstructor
public class ReservationResponse {
	
	private Long id;
	private String reservationDate;
	private String reservationTime;
	private String reservationStatus;
	private String userName;
	private String placeName;
	private String orderNote;
	private int count;
	
	public ReservationResponse(Reservation reservation) {
		this.id = reservation.getId();
		this.reservationDate = formatReservationDate(reservation.getReservationDate());
		this.reservationTime = formatReservationTime(reservation.getReservationTime());
		this.reservationStatus = reservation.getCode().getValue();
		this.userName = reservation.getUser().getName();
	    this.placeName = reservation.getPlace().getName();
	    this.orderNote = reservation.getOrderNote();
	    this.count = reservation.getCount();
	     
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
