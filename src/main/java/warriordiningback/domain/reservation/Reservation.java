package warriordiningback.domain.reservation;

import java.util.Date;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import warriordiningback.domain.BaseEntity;
import warriordiningback.domain.Code;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.user.User;

@Getter
@ToString
@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {
	
	@Column(name = "reservation_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date reservationDate;
	
	@Column(name = "reservation_time", nullable = false)
	@JsonFormat(pattern = "HH:mm")
	private Date reservationTime;
	
	@Column(nullable = false)
	private int count;
	
	@Column(name = "order_note")
	private String orderNote;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;
	
	@ManyToOne
	@JoinColumn(name = "status", nullable = false)
	private Code code;
	
	public void updateStatus(Code code) {
		this.code = code;
	}

	public void create(Date reservationDate, Date reservationTime, int count, String orderNote, User user, Place place, Code code) {
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
		this.count = count;
		this.orderNote = orderNote;
		this.user = user;
		this.place = place;
		this.code = code;
	}

}
