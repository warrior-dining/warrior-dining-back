package warriordiningback.domain.reservation;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import warriordiningback.domain.BaseEntity;
import warriordiningback.domain.Code;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.user.User;

@Getter
@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {
	
	@Column(name = "reservation_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date reservationDate;
	
	@Column(name = "reservation_time", nullable = false)
	@JsonFormat(pattern = "HH:mm:ss")
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
	
}
