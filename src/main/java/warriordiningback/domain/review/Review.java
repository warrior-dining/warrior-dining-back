package warriordiningback.domain.review;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
import warriordiningback.domain.place.Place;
import warriordiningback.domain.reservation.Reservation;
import warriordiningback.domain.user.User;

@Getter
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

	@Column(nullable = false)
	private int rating;
	
	@Column(nullable = false)
	private String content;
	
	@Column(name = "is_deleted")
	private boolean isDeleted;

	@ManyToOne
	@JoinColumn(name = "reservation_id", nullable = false)
	private Reservation reservation;
	
	@ManyToOne
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public void create(int rating, String content, Reservation reservation, Place place, User user) {
		this.rating = rating;
		this.content = content;
		this.reservation = reservation;
		this.place = place;
		this.user = user;
	}
	
	public void updateIsdelete(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public void update(int rating, String content) {
		this.rating = rating;
		this.content = content;
	}

}
