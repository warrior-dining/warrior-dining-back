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
import warriordiningback.domain.Code;
import warriordiningback.domain.place.Place;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.user.User;

@Getter
@Entity
@Table(name = "reservations")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
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
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false,  updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt; 
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;
	
	@ManyToOne
	@JoinColumn(name = "status", nullable = false)
	private Code code;
	
	public static Reservation updateStatus(Long id, Date reservationDate, Date reservationTime, int count, String orderNote, User user, Place place, Code code) {
		Reservation updateReservations = new Reservation();
		updateReservations.id = id;
		updateReservations.reservationDate = reservationDate;
		updateReservations.reservationTime = reservationTime;
		updateReservations.count = count;
		updateReservations.orderNote = orderNote;
		updateReservations.updatedAt = LocalDateTime.now();
		updateReservations.user = user;
		updateReservations.place = place;
		updateReservations.code = code;
		return updateReservations;
	}
	
}
