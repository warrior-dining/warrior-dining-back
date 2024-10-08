package warriordiningback.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import warriordiningback.domain.reservation.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

}
